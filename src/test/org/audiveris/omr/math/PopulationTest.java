//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                                  P o p u l a t i o n T e s t                                   //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//
//  Copyright © Hervé Bitteur and others 2000-2017. All rights reserved.
//
//  This program is free software: you can redistribute it and/or modify it under the terms of the
//  GNU Affero General Public License as published by the Free Software Foundation, either version
//  3 of the License, or (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
//  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
//  See the GNU Affero General Public License for more details.
//
//  You should have received a copy of the GNU Affero General Public License along with this
//  program.  If not, see <http://www.gnu.org/licenses/>.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package org.audiveris.omr.math;

import static junit.framework.Assert.*;

//import org.testng.annotations.*;
import org.audiveris.omr.math.Population;
import org.audiveris.omr.util.BaseTestCase;

/**
 * Class <code>PopulationTest</code> performs unit tests on Population class.
 *
 * @author Hervé Bitteur
 */
public class PopulationTest
        extends BaseTestCase
{
    //~ Methods ------------------------------------------------------------------------------------

    //-----------//
    // testEmpty //
    //-----------//
    //@Test
    public void testEmpty ()
    {
        Population p = new Population();

        assertEquals("No values cumulated so far.", 0, p.getCardinality());

        try {
            double mv = p.getMeanValue();
            fail(
                    "Exception should be raised"
                    + " when retrieving mean value of an empty population");
        } catch (Exception expected) {
            checkException(expected);
        }

        try {
            double sd = p.getStandardDeviation();
            fail(
                    "Exception should be raised"
                    + " when retrieving standard deviation of an empty population");
        } catch (Exception expected) {
            checkException(expected);
        }

        try {
            double v = p.getVariance();
            fail("Exception should be raised" + " when retrieving variance of an empty population");
        } catch (Exception expected) {
            checkException(expected);
        }

        try {
            p.excludeValue(123);
            fail("Exception should be raised" + " when excluding a value from an empty population");
        } catch (Exception expected) {
            checkException(expected);
        }
    }

    //-------------//
    // testExclude //
    //-------------//
    //@Test
    public void testExclude ()
    {
        Population p = new Population();
        p.includeValue(5);
        p.includeValue(6);
        p.includeValue(8);
        p.includeValue(9);

        p.excludeValue(5);
        p.excludeValue(9);

        assertEquals("Population should contain 2 values.", 2, p.getCardinality());

        assertEquals("Check mean value.", 7d, p.getMeanValue());

        double v = p.getVariance();
        //assertEquals("Check variance of 2 values.", 1d, v); // Biased
        assertEquals("Check variance of 2 values.", 2d, v); // Unbiased

        double sd = p.getStandardDeviation();
        ///assertNears("Check standard deviation of 2 values.", Math.sqrt(1d), sd); // Biased
        assertNears("Check standard deviation of 2 values.", Math.sqrt(2d), sd); // Unbiased
    }

    //-------------//
    // testInclude //
    //-------------//
    //@Test
    public void testInclude ()
    {
        Population p = new Population();
        p.includeValue(5);
        p.includeValue(6);
        p.includeValue(8);
        p.includeValue(9);

        assertEquals("Population should contain 4 values.", 4, p.getCardinality());

        assertEquals("Check mean value.", 7d, p.getMeanValue());

        double v = p.getVariance();
        //assertEquals("Check variance of 4 values.", 2.5, p.getVariance()); // Biased
        assertEquals("Check variance of 4 values.", 3.33, p.getVariance(), 0.01); // Unbiased

        double sd = p.getStandardDeviation();
        //assertEquals("Check standard deviation of 4 values.", Math.sqrt(2.5), sd, 0.01); // Biased
        assertEquals("Check standard deviation of 4 values.", Math.sqrt(3.33), sd, 0.01); // Unbiased
    }

    //---------------//
    // testSingleton //
    //---------------//
    //@Test
    public void testSingleton ()
    {
        Population p = new Population();
        double val = 123d;
        p.includeValue(val);

        assertEquals("Population should contain one value.", 1, p.getCardinality());

        assertEquals("Check mean value.", val, p.getMeanValue());

        double var = p.getVariance();
        assertEquals("Check variance.", 0.0, var);

        double std = p.getStandardDeviation();
        assertEquals("Check standard deviation.", 0.0, std);

        p.excludeValue(val);
        assertEquals("Population should contain no value.", 0, p.getCardinality());
    }
}