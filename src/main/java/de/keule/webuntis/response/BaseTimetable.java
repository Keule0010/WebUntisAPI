package de.keule.webuntis.response;

import java.util.Comparator;
import java.util.List;

public abstract class BaseTimetable {
	protected List<Period> periods;

	public Period getFirstPeriod() {
		if (periods.isEmpty())
			return null;
		Period p = periods.get(0);
		for (Period period : periods)
			if (period.getStartTime() < p.getStartTime())
				p = period;
		return p;
	}

	public Period getFirstRegularPeriod() {
		if (periods.isEmpty())
			return null;
		
		Period p = null;
		for(int i = 0; i < periods.size(); i++) {
			if(periods.get(i).isRegular())
				p = periods.get(i);
		}
		if(p == null)
			return null;
		
		for (Period period : periods) {
			if (period.isRegular() && period.getStartTime() < p.getStartTime())
				p = period;
		}
		
		if (p.isRegular())
			return p;
		else
			return null;
	}

	public Period getLastPeriod() {
		if (periods.isEmpty())
			return null;
		Period p = periods.get(0);
		for (Period period : periods)
			if (period.getStartTime() > p.getStartTime())
				p = period;
		return p;
	}

	public void sortByStartTime() {
		periods.sort(Comparator.comparing(Period::getStartTime));
	}

	public void sortByEndTime() {
		periods.sort((a, b) -> {
			if (a.getStartTime() == b.getStartTime())
				return 0;
			else if (a.getStartTime() < b.getStartTime())
				return 1;
			else
				return -1;
		});
	}

	public List<Period> getPeriods() {
		return periods;
	}
}
