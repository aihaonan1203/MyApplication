package com.gaotai.baselib.util;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期
 */
public enum DateField {

	YEAR {

		public int milliseconds() {
			return 365 * DATE.milliseconds();
		}

		public long difference(Date begin, Date end) {
			return DcDateUtils.getField(end, this)
					- DcDateUtils.getField(begin, this);
		}
	},
	MONTH {

		public int milliseconds() {
			return 30 * DATE.milliseconds();
		}

		public long difference(Date begin, Date end) {
			return 12 * YEAR.difference(begin, end)
					+ DcDateUtils.getField(end, this)
					- DcDateUtils.getField(begin, this);
		}
	},
	WEEK_OF_YEAR {

		public int milliseconds() {
			return 0;
		}
	},
	WEEK_OF_MONTH {

		public int milliseconds() {
			return 0;
		}
	},
	WEEK {

		public int milliseconds() {
			return 7 * DATE.milliseconds();
		}
	},
	DATE {

		public int milliseconds() {
			return 24 * HOUR.milliseconds();
		}
	},
	DAY_OF_YEAR {

		public int milliseconds() {
			return 0;
		}
	},
	DAY_OF_WEEK {

		public int milliseconds() {
			return 0;
		}
	},
	HOUR {

		public int milliseconds() {
			return 60 * MINUTE.milliseconds();
		}
	},
	MINUTE {

		public int milliseconds() {
			return 60 * SECOND.milliseconds();
		}
	},
	SECOND {

		public int milliseconds() {
			return 1000;
		}
	},
	MILLISECOND {

		public int milliseconds() {
			return 1;
		}
	};

	public int value() {
		switch (this) {
		case YEAR:
			return Calendar.YEAR;
		case MONTH:
			return Calendar.MONTH;
		case WEEK_OF_YEAR:
			return Calendar.WEEK_OF_YEAR;
		case WEEK_OF_MONTH:
			return Calendar.WEEK_OF_MONTH;
		case WEEK:
			return Calendar.WEEK_OF_YEAR;
		case DATE:
			return Calendar.DATE;
		case DAY_OF_YEAR:
			return Calendar.DAY_OF_YEAR;
		case DAY_OF_WEEK:
			return Calendar.DAY_OF_WEEK;
		case HOUR:
			return Calendar.HOUR;
		case MINUTE:
			return Calendar.MINUTE;
		case SECOND:
			return Calendar.SECOND;
		case MILLISECOND:
			return Calendar.MILLISECOND;
		default:
			return 0;
		}
	}

	public abstract int milliseconds();

	public long difference(Date begin, Date end) {
		return (end.getTime() - begin.getTime()) / milliseconds();
	}
}
