package com.everyware.syslog_ng;

import java.util.Date;

public class SyslogParser {
	public static Message parse(String msg) {
		int split = msg.indexOf(' ');

		if(split == -1)
			throw new IllegalArgumentException("invalid msg");

		String rawDate = msg.substring(0, split);

		Date date = javax.xml.bind.DatatypeConverter
				.parseDateTime(rawDate).getTime();

		int end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		int priority = Integer.parseInt(msg.substring(split + 1, end));
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		String level = msg.substring(split + 1, end);
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		String host = msg.substring(split + 1, end);
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		String src = msg.substring(split + 1, end);
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		int facilityNum = Integer.parseInt(msg.substring(split + 1,
					end));
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		String facility = msg.substring(split + 1, end);
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		String program = msg.substring(split + 1, end);
		split = end;

		end = msg.indexOf(' ', split + 1);
		if(end == -1)
			throw new IllegalArgumentException("invalid msg");
		long pid = Long.parseLong(msg.substring(split + 1, end));
		split = end;

		String message = msg.substring(split + 1);

		return new Message(date, host, src, priority, level,
				facilityNum, facility, program, pid, message);
	}
}
