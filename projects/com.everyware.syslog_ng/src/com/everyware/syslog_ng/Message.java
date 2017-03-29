package com.everyware.syslog_ng;

import java.util.Date;

public class Message {
	private Date date;
	private String host;
	private String src;
	private int priority;
	private String level;
	private int facilityNum;
	private String facility;
	private String program;
	private long pid;
	private String message;

	public Message(Date date, String host, String src, int priority, String
			level, int facilityNum, String facility, String program,
			long pid, String message) {
		this.date = date;
		this.host = host;
		this.src = src;
		this.priority = priority;
		this.level = level;
		this.facilityNum = facilityNum;
		this.facility = facility;
		this.program = program;
		this.pid = pid;
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public String getHost() {
		return host;
	}

	public String getSource() {
		return src;
	}

	public int getPriority() {
		return priority;
	}

	public String getLevel() {
		return level;
	}

	public int getFacilityNum() {
		return facilityNum;
	}

	public String getFacility() {
		return facility;
	}

	public String getProgram() {
		return program;
	}

	public long getPid() {
		return pid;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Message[date=" + date
			+ ";host=" + host
			+ ";src=" + src
			+ ";priority=" + priority
			+ ";level=" + level
			+ ";facilityNum=" + facilityNum
			+ ";facility=" + facility
			+ ";program=" + program
			+ ";pid=" + pid
			+ ";msg=" + message + "]";
	}
}
