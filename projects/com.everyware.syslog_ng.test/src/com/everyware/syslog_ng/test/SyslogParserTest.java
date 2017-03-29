package com.everyware.syslog_ng.test;

import org.junit.Test;
import static org.junit.Assert.*;

import com.everyware.syslog_ng.Message;
import com.everyware.syslog_ng.SyslogParser;

public class SyslogParserTest {
	@Test
	public void testParser() {
		String syslogMsg = "2017-03-28T21:56:05+00:00 85 notice syslog syslog.novalocal 10 authpriv polkitd 504 Unregistered Authentication Agent for unix-process:10647:607796 (system bus name :1.44, object path /org/freedesktop/PolicyKit1/AuthenticationAgent, locale en_US.UTF-8) (disconnected from bus)";
		Message msg = SyslogParser.parse(syslogMsg);
		assertEquals(1490738165000L, msg.getDate().getTime());
		assertEquals(85, msg.getPriority());
		assertEquals("notice", msg.getLevel());
		assertEquals("syslog", msg.getHost());
		assertEquals("syslog.novalocal", msg.getSource());
		assertEquals(10, msg.getFacilityNum());
		assertEquals("authpriv", msg.getFacility());
		assertEquals("polkitd", msg.getProgram());
		assertEquals(504L, msg.getPid());
		assertEquals("Unregistered Authentication Agent for unix-process:10647:607796 (system bus name :1.44, object path /org/freedesktop/PolicyKit1/AuthenticationAgent, locale en_US.UTF-8) (disconnected from bus)", msg.getMessage());
	}
}
