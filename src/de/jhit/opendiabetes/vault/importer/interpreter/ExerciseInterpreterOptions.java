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
package de.jhit.opendiabetes.vault.importer.interpreter;

import java.util.Date;

/**
 *
 * @author Jens
 */
public class ExerciseInterpreterOptions extends InterpreterOptions {
    
    public final int activityThreshold;
    public final int activitySliceThreshold;
    
    public ExerciseInterpreterOptions(boolean isImportPeriodRestricted, 
            Date importPeriodFrom, Date importPeriodTo,
            int activityThreshold,
            int activitySliceThreshold) {
        super(isImportPeriodRestricted, importPeriodFrom, importPeriodTo);
        this.activityThreshold = activityThreshold;
        this.activitySliceThreshold = activitySliceThreshold;
    }
    
}
