/*
 * Copyright (C) 2017 Jens Heuschkel
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jhit.opendiabetes.vault.processing;

import de.jhit.opendiabetes.vault.container.SliceEntry;
import de.jhit.opendiabetes.vault.container.VaultEntry;
import de.jhit.opendiabetes.vault.processing.filter.Filter;
import de.jhit.opendiabetes.vault.processing.filter.FilterResult;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author juehv
 */
public class DataSlicer {

    private final List<Filter> registeredFilter = new ArrayList<>();
    private final DataSlicerOptions options;

    public DataSlicer(DataSlicerOptions options) {
        this.options = options;
    }

    /**
     * Slices dataset with respect to registered filters.
     *
     * @param data the data set which should be filtered
     * @return a list of slice entrys matching the registered filters or an
     * empty list if no filter matches
     */
    public List<SliceEntry> sliceData(List<VaultEntry> data) {
        List<SliceEntry> retVal = new ArrayList<>();
        FilterResult lastResult = null;

        for (Filter filter : registeredFilter) {
            if (lastResult == null) {
                lastResult = filter.filter(data);
            } else {
                lastResult = filter.filter(lastResult.filteredData);
            }

        }
        // retVal.add(StartTimeseries ,Startpoint);
        // TODO generate slice file 
        // TODO add output stage filter 
        // --> first of series
        // --> last of series
        // --> mid point of series
        // --> none (would return for every point in the series a slice entry)

        Long Duration = null;
        Date Timestart = null, TimeMid = null, TimeEnd = null;

        if ((registeredFilter.get(0)).toString().contains("NoneFilter")) {
            Timestart = lastResult.filteredData.get(0).getTimestamp();
            TimeEnd = lastResult.filteredData.get(lastResult.filteredData.size() - 1).getTimestamp();;
            Duration = TimeEnd.getTime() - Timestart.getTime();
            for (VaultEntry entry : data) {
                retVal.add(new SliceEntry(entry.getTimestamp(), Duration));
            }

        } else {
            if (lastResult.filteredData != null && lastResult.timeSeries != null && lastResult.filteredData.size() > 0) {
                Timestart = lastResult.filteredData.get(0).getTimestamp();
                int temp;
                temp = ((lastResult.filteredData.size())) / 2;
                TimeMid = lastResult.filteredData.get(temp).getTimestamp();
                TimeEnd = lastResult.filteredData.get(lastResult.filteredData.size() - 1).getTimestamp();;
                Duration = TimeEnd.getTime() - Timestart.getTime();
                retVal.add(new SliceEntry(Timestart, Duration));
                retVal.add(new SliceEntry(TimeMid, Duration));
                retVal.add(new SliceEntry(TimeEnd, Duration));
            }

        }
        return retVal;
    }

    /**
     * Registeres a filter for slicing. Should be called before slicing.
     * Registered filteres are always combined as logical AND.
     *
     * @param filter
     */
    public void registerFilter(Filter filter) {
        registeredFilter.add(filter);
    }
}
