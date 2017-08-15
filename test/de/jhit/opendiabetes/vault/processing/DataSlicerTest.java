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

import de.jhit.opendiabetes.vault.container.SliceEntry;
import de.jhit.opendiabetes.vault.container.VaultEntryType;
import de.jhit.opendiabetes.vault.processing.filter.Filter;
import de.jhit.opendiabetes.vault.processing.filter.FilterType;
import de.jhit.opendiabetes.vault.processing.filter.NoneFilter;
import de.jhit.opendiabetes.vault.processing.filter.OverThresholdFilter;
import de.jhit.opendiabetes.vault.processing.filter.TimePointFilter;
import de.jhit.opendiabetes.vault.processing.filter.TimeSpanFilter;
import de.jhit.opendiabetes.vault.processing.filter.UnderThresholdFilter;
import de.jhit.opendiabetes.vault.testhelper.StaticDataset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author juehv
 */
public class DataSlicerTest extends Assert {

    public DataSlicerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of sliceData method, of class DataSlicer.
     */
    @Test
    public void testNoneData() throws ParseException {
        System.out.println("None Filter Test");
        Filter filter = new NoneFilter();
        DataSlicerOptions options = new DataSlicerOptions(0, 60);
        DataSlicer instance = new DataSlicer(options);
        instance.registerFilter(filter);

        List<SliceEntry> expResult = new ArrayList<>(); // null;
        List<SliceEntry> result = instance.sliceData(StaticDataset.getStaticDataset());

        for (SliceEntry res : result) {
            expResult.add(res);
        }

        for (int i = 0; i < result.size() - 1; i++) {
                
            //SliceEntry contains Date Timestamp and long Duration. Hence both have been checked for Testing
            assertEquals(expResult.get(i).getTimestamp(), result.get(i).getTimestamp());
            assertEquals(expResult.get(i).getDuration(), result.get(i).getDuration());
        }

    }

    @Test
    public void testTimeSpanData() throws ParseException {
        System.out.println("Time Span Filter");
        LocalTime startTime = LocalTime.parse("04:46:00");//  TimestampUtils.createCleanTimestamp("2017.06.29-04:46", "yyyy.MM.dd-HH:mm")
        LocalTime endTime = LocalTime.parse("09:46:00");
        Filter filter = new TimeSpanFilter(startTime, endTime);

        DataSlicerOptions options = new DataSlicerOptions(0, 60);
        DataSlicer instance = new DataSlicer(options);
        instance.registerFilter(filter);

        List<SliceEntry> expResult = new ArrayList<>();// null;

       /**************
        * To check expected result beforehand timestamp and duration 
        * are generated depending on what criteria has been passed
        * to which filter. If criteria is changed these values should be changed
        */
        String DateString1 = "06/29/2017 4:46:00 AM";
        String DateString2 = "06/29/2017 6:26:00 AM";
        String DateString3 = "06/29/2017 9:00:00 AM";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        df.setLenient(false);
        Date date1 = df.parse(DateString1);
        Date date2 = df.parse(DateString2);
        Date date3 = df.parse(DateString3);

        expResult.add(new SliceEntry(date1, 15240000));
        expResult.add(new SliceEntry(date2, 15240000));
        expResult.add(new SliceEntry(date3, 15240000));

        List<SliceEntry> result = instance.sliceData(StaticDataset.getStaticDataset());
        for (int i = 0; i < result.size() - 1; i++) {

            //SliceEntry contains Date Timestamp and long Duration. Hence both have been checked for Testing
            assertEquals(expResult.get(i).getTimestamp(), result.get(i).getTimestamp());
            assertEquals(expResult.get(i).getDuration(), result.get(i).getDuration());
        }
    }

    @Test
    public void testTimePointData() throws ParseException {
        System.out.println("Time Point Filter");
        LocalTime startTime = LocalTime.parse("04:46:00");// /*"04:46:00"*/  TimestampUtils.createCleanTimestamp("2017.06.29-04:46", "yyyy.MM.dd-HH:mm")        
        Filter filter = new TimePointFilter(startTime, 12);
        DataSlicerOptions options = new DataSlicerOptions(0, 60);
        DataSlicer instance = new DataSlicer(options);
        instance.registerFilter(filter);

        List<SliceEntry> expResult = new ArrayList<>();//  null;

        /**************
        * To check expected result beforehand timestamp and duration 
        * are generated depending on what criteria has been passed
        * to which filter. If criteria is changed these values should be changed
        */
        String DateString1 = "06/29/2017 4:46:00 AM";
        String DateString2 = "06/29/2017 4:56:00 AM";
        String DateString3 = "06/29/2017 4:58:00 AM";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        df.setLenient(false);
        Date date1 = df.parse(DateString1);
        Date date2 = df.parse(DateString2);
        Date date3 = df.parse(DateString3);

        expResult.add(new SliceEntry(date1, 720000));
        expResult.add(new SliceEntry(date2, 720000));
        expResult.add(new SliceEntry(date3, 720000));

        List<SliceEntry> result = instance.sliceData(StaticDataset.getStaticDataset());
        for (int i = 0; i < result.size() - 1; i++) {

           //SliceEntry contains Date Timestamp and long Duration. Hence both have been checked for Testing
            assertEquals(expResult.get(i).getTimestamp(), result.get(i).getTimestamp());
            assertEquals(expResult.get(i).getDuration(), result.get(i).getDuration());
        }
    }

    @Test
    public void testSliceOverheadTHData() throws ParseException {
        System.out.println("Over head Filter");
        Filter filter = new OverThresholdFilter(VaultEntryType.BASAL_PROFILE, 1.00, FilterType.BASAL_AVAILABLE, FilterType.BASAL_TH);
        DataSlicerOptions options = new DataSlicerOptions(0, 60);
        DataSlicer instance = new DataSlicer(options);
        instance.registerFilter(filter);

        List<SliceEntry> expResult = new ArrayList<>();

        /**************
        * To check expected result beforehand timestamp and duration 
        * are generated depending on what criteria has been passed
        * to which filter. If criteria is changed these values should be changed
        */
        String DateString1 = "06/29/2017 05:00:00 AM";
        String DateString2 = "06/29/2017 08:00:00 AM";
        String DateString3 = "06/29/2017 10:00:00 AM";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        df.setLenient(false);
        Date date1 = df.parse(DateString1);
        Date date2 = df.parse(DateString2);
        Date date3 = df.parse(DateString3);

        expResult.add(new SliceEntry(date1, 18000000));
        expResult.add(new SliceEntry(date2, 18000000));
        expResult.add(new SliceEntry(date3, 18000000));

        List<SliceEntry> result = instance.sliceData(StaticDataset.getStaticDataset());
        for (int i = 0; i < result.size() - 1; i++) {

            //SliceEntry contains Date Timestamp and long Duration. Hence both have been checked for Testing
            assertEquals(expResult.get(i).getTimestamp(), result.get(i).getTimestamp());
            assertEquals(expResult.get(i).getDuration(), result.get(i).getDuration());
        }
    }

    @Test
    public void testSliceUnderheadTHData() throws ParseException {
        System.out.println("under head filter");

        Filter filter = new UnderThresholdFilter(VaultEntryType.STRESS, 25.00, FilterType.STRESS_AVAILABLE, FilterType.STRESS_TH);
        DataSlicerOptions options = new DataSlicerOptions(0, 60);
        DataSlicer instance = new DataSlicer(options);
        instance.registerFilter(filter);

        List<SliceEntry> expResult = new ArrayList<>();

        /**************
        * To check expected result beforehand timestamp and duration 
        * are generated depending on what criteria has been passed
        * to which filter. If criteria is changed these values should be changed
        */
        String DateString1 = "06/29/2017 04:46:00 AM";
        String DateString2 = "06/29/2017 08:16:00 AM";
        String DateString3 = "06/29/2017 12:21:00 AM";
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        df.setLenient(false);
        Date date1 = df.parse(DateString1);
        Date date2 = df.parse(DateString2);
        Date date3 = df.parse(DateString3);

        expResult.add(new SliceEntry(date1, 27300000));
        expResult.add(new SliceEntry(date2, 27300000));
        expResult.add(new SliceEntry(date3, 27300000));

        List<SliceEntry> result = instance.sliceData(StaticDataset.getStaticDataset());

        for (int i = 0; i < result.size() - 1; i++) {
            //SliceEntry contains Date Timestamp and long Duration. Hence both have been checked for Testing
            assertEquals(expResult.get(i).getTimestamp(), result.get(i).getTimestamp());
            assertEquals(expResult.get(i).getDuration(), result.get(i).getDuration());
        }

    }

}
