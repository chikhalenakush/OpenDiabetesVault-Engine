/*
 * Copyright (C) 2017 juehv
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

/**
 *
 * @author juehv
 */
class DataSlicerOptions {

//    public final long margin;
//    public final long duration;
//
//    public DataSlicerOptions(long margin, long duration) {
//        this.margin = margin;
//        this.duration = duration;
//    }
    
    
    public static enum OutputFilter {
        FIRST_OF_SERIES, MID_OF_SERIES, END_OF_SERIES;
    }

    public final long duration;
    public final OutputFilter outputFilter;

    public DataSlicerOptions(long duration, OutputFilter outputFilter) {
        this.duration = duration;
        this.outputFilter = outputFilter;
    }

}
