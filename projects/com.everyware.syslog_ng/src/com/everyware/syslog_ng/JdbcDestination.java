package com.everyware.syslog_ng;

import org.syslog_ng.InternalMessageSender;
import org.syslog_ng.TextLogDestination;

import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;

/*
 * mkdir -p /opt/syslog-ng-jdbc
 * wget -O /opt/syslog-ng-jdbc/db2jcc4.jar http://repository.linz.lan/db2/db2jcc4.jar
 * wget -O /opt/syslog-ng-jdbc/db2jcc_license_cisuz.jar http://repository.linz.lan/db2/db2jcc_license_cisuz.jar
 * destination d_java {
 * 	java(
 * 		class_path("/opt/syslog-ng-jdbc/db2jcc4.jar:/opt/syslog-ng-jdbc/db2jcc_license_cisuz.jar:/opt/syslog-ng-jdbc/syslog_ng_jdbc.jar")
 * 		class_name("com.everyware.syslog_ng.JdbcDestination")
 * 		template("${ISODATE} ${PRI:-00} ${LEVEL} ${FULLHOST_FROM:-unknown} ${HOST:-unknown} ${FACILITY_NUM:-0} ${FACILITY:-unknown} ${PROGRAM:-unknown} ${PID:-0} ${MSGONLY}")
 * 		option("driver", "com.ibm.db2.jcc.DB2Driver")
 * 		option("url", "jdbc:db2://db2node01.db2.lan:50000/evryware:currentSchema=RSYSLOG;enableSysplexWLB=true;")
 * 		option("username", "rsyslog")
 * 		option("password", "top-secret")
 * 	);
 * };
 * log { source(s_sys); destination(d_java); };
 */

public class JdbcDestination extends TextLogDestination {
	private static final String STATEMENT = "INSERT INTO MESSAGES (TIME, "
			+ "HOST, SOURCE, PRIORITY, LEVEL, FACILITY_NUM, "
			+ "FACILITY, PROGRAM, PID, MESSAGE) VALUES "
			+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private String url;
	private String username;
	private String password;
	private String driver;
	private String prefix;

	private Connection connection = null;
	private PreparedStatement statement;

	public JdbcDestination(long ref) {
		super(ref);
	}

	@Override
	public boolean init() {
		driver = getOption("driver");
		url = getOption("url");
		username = getOption("username");
		password = getOption("password");

		try {
			Class.forName(driver);
		} catch(Throwable t) {
			InternalMessageSender.error("Error loading JDBC driver: "
					+ t.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public void deinit() {
	}

	@Override
	public boolean open() {
		try {
			connection = DriverManager.getConnection(url, username,
					password);
			connection.setAutoCommit(true);
		} catch(SQLException e) {
			InternalMessageSender.error("Error opening connection: "
					+ e.getMessage());
			return false;
		}

		try {
			statement = connection.prepareStatement(STATEMENT);
		} catch(SQLException e) {
			InternalMessageSender.error("Error preparing statement: "
					+ e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public boolean isOpened() {
		return connection != null;
	}

	@Override
	public void close() {
		try {
			statement.close();
		} catch(Throwable t) {
			InternalMessageSender.error("Error closing statement: "
					+ t.getMessage());
		}
		try {
			connection.close();
		} catch(Throwable t) {
			InternalMessageSender.error("Error closing connection: "
					+ t.getMessage());
		}
		connection = null;
	}

	@Override
	public boolean send(String message) {
		Message msg = SyslogParser.parse(message);
		try {
			statement.setDate(1, new Date(msg.getDate().getTime()));
			statement.setString(2, msg.getHost());
			statement.setString(3, msg.getSource());
			statement.setInt(4, msg.getPriority());
			statement.setString(5, msg.getLevel());
			statement.setInt(6, msg.getFacilityNum());
			statement.setString(7, msg.getFacility());
			statement.setString(8, msg.getProgram());
			statement.setLong(9, msg.getPid());
			statement.setString(10, msg.getMessage());
			statement.executeUpdate();
		} catch(SQLException e) {
			InternalMessageSender.error("Error executing statement: "
					+ e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public String getNameByUniqOptions() {
		return "JdbcDestination[" + username + "@" + url + "]";
	}
}
