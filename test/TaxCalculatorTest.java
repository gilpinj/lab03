import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;

/**
 * This class provides TestNG tests for the tax calculator. The tests exercise
 * the tax calculator to determine if the calculator is working properly.
 *
 * @author schilling
 */
public class TaxCalculatorTest {

    /**
     * This is a private variable which holds the tax calculator interface.
     */
    private TaxCalculatorInterface calculator;


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFirstNameOnlyForSingleFilerShouldThrowException() throws IllegalArgumentException {
        calculator = new TaxCalculator("Bob", TaxCalculatorInterface.SINGLE, 21);
    }


    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNoNameProvidedForMarriedFilerShouldThrowException() throws IllegalArgumentException {
        calculator = new TaxCalculator("", 21, TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 21);
    }


    @DataProvider(name = "taxFilingStatusDataProvider")
    public Object[][] taxFilingStatusDataProvider() {
        return new Object[][]{new Object[]{TaxCalculatorInterface.SINGLE, 30, -1},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, -1},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, -1},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, -1},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40, -1},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40, -1},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40, -1},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70, -1},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70, -1},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70, -1},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 60},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 65},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 70},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1},};
    }

    @Test(dataProvider = "taxFilingStatusDataProvider")
    public void testStatus(int status, int age, int spouseAge) {
        if (spouseAge != -1) {
            // Instantiate a new tax calculator with a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);
        } else {
            // Instantiate a new tax calculator without a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status);
        }

        // Now check to see whether the return is required.
        assertEquals(status, status);
    }

    @DataProvider(name = "singleStatusIsReturnRequiredDataProvider")
    public Object[][] singleStatusIsReturnRequiredDataProvider() {
        return new Object[][]{new Object[]{TaxCalculatorInterface.SINGLE, 30, 8949.00, false},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 8950.00, true},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 8951.00, true},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, 10299.00, false},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, 10300.00, true},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, 10301.00, true},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40, 11499.00, false},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40, 11500.00, true},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40, 11501.00, true},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70, 12849.00, false},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70, 12850.00, true},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70, 12851.00, true},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 14399.00, false},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 14400.00, true},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 14401.00, true},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 15449.00, false},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 15450.00, true},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 15451.00, true}};
    }

    @Test(dataProvider = "singleStatusIsReturnRequiredDataProvider")
    public void testisReturnRequiredSingleStatus(int status, int age, double income, boolean returnRequired) {
        // Instantiate a new tax calculator.
        calculator = new TaxCalculator("Bob SMith", age, status);

        // Now set the income.
        calculator.setGrossIncome(income);

        // Now check to see whether the return is required.
        assertEquals(calculator.isReturnRequired(), returnRequired);
    }

    @DataProvider(name = "marriedStatusIsReturnRequiredDataProvider")
    public Object[][] marriedStatusIsReturnRequiredDataProvider() {
        return new Object[][]{new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40, 17899.00, false},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40, 17900.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40, 17901.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63, 18949.00, false},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63, 18950.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63, 18951.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69, 19999.00, false},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69, 20000.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69, 20001.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 60, 3499.00, false},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 60, 3500.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 60, 3501.00, true},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 70, 70, 3501.00, true},

        };
    }

    @Test(dataProvider = "marriedStatusIsReturnRequiredDataProvider")
    public void testIsReturnRequiredSingleStatus(int status, int age, int spouseAge, double income,
                                                 boolean returnRequired) throws IllegalArgumentException {
        // Instantiate a new tax calculator.
        calculator = new TaxCalculator("Bob SMith", status, age, spouseAge);

        // Now set the income.
        calculator.setGrossIncome(income);

        // Now check to see whether the return is required.
        assertEquals(calculator.isReturnRequired(), returnRequired);

    }

    @DataProvider(name = "standardDeductionDataProvider")
    public Object[][] standardDeductionDataProvider() {
        return new Object[][]{new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 5450.00},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, -1, 6500.00},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40, 10900.00},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 70, 70, 6500.00},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 11950.00},
        };
    }

    @Test(dataProvider = "standardDeductionDataProvider")
    public void testIsStandardDeduction(int status, int age, int spouseAge, double standardDeduction) {
        if (spouseAge != -1) {
            // Instantiate a new tax calculator with a spouse.
            calculator = new TaxCalculator("Bob Smith", status, age, spouseAge);
        } else {
            // Instantiate a new tax calculator without a spouse.
            calculator = new TaxCalculator("Bob Smith", status, age);
        }

        // Now check to see whether the return is required.
        assertEquals(calculator.getStandardDeduction(), standardDeduction, 1.00);
    }

    @DataProvider(name = "taxableIncomeDataProvider")
    public Object[][] taxableIncomeDataProvider() {
        return new Object[][]{new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 5450.00, 0.00, 0},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 5451.00, 1.00, 0.1},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 13474.00, 8024.00, 802.4},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 13475.00, 8025.00, 802.5},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 13476.00, 8026.00, 802.65},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 37999.00, 32549.00, 4481.1},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 38000.00, 32550.00, 4481.25},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 38001.00, 32551.00, 4481.5},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 84299.00, 78849.00, 16056},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 84300.00, 78850.00, 16056.25},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 84301.00, 78851.00, 16056.53},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 169999.00, 164549.00, 40051.97},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 170000.00, 164550.00, 40052.25},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 170001.00, 164551.00, 40052.58},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 363149.00, 357699.00, 103791.42},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 363150.00, 357700.00, 103791.75},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, -1, 363151.00, 357701.00, 103792.1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 26949.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 26950.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 26951.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 75999.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 76000.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 76001.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 142349.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 142350.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 142351.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 211199.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 211200.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 211201.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 368599.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 368600.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, -1, 368601.00, 357701.00, 96770.35},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 27999.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 28000.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 28001.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 77049.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 77050.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 77051.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 143399.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 143400.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 143401.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 212249.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 212250.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 212251.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 369649.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 369650.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, -1, 369651.00, 357701.00, 96770.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 26949.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 26950.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 26951.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 75999.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 76000.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 76001.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 142349.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 142350.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 142351.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 211199.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 211200.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 211201.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 368599.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 368600.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 29, 368601.00, 357701.00, 96770.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 27999.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 28000.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 28001.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 77049.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 77050.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 77051.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 143399.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 143400.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 143401.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 212249.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 212250.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 212251.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 369649.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 369650.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 62, 369651.00, 357701.00, 96770.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 27999.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 28000.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 28001.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 77049.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 77050.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 77051.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 143399.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 143400.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 143401.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 212249.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 212250.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 212251.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 369649.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 369650.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 62, 70, 369651.00, 357701.00, 96770.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 29049.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 29050.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 29051.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 78099.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 78100.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 78101.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 144449.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 144450.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 144451.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 213299.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 213300.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 213301.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 370699.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 370700.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 70, 370701.00, 357701.00, 96770.35},


        };
    }

    @Test(dataProvider = "taxableIncomeDataProvider")
    public void testGetTaxableIncome(int status, int age, int spouseAge, double grossIncome, double taxableIncome,
                                     double taxDue) throws Exception {
        if (spouseAge != -1) {
            // Instantiate a new tax calculator with a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);
        } else {
            // Instantiate a new tax calculator without a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status);
        }

        // Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Now check the taxable income.
        assertEquals(calculator.getTaxableIncome(), taxableIncome);
    }

    @Test(dataProvider = "taxableIncomeDataProvider")
    public void testGetNetTaxRate(int status, int age, int spouseAge, double grossIncome, double taxableIncome,
                                  double taxDue) {
        if (spouseAge != -1) {
            // Instantiate a new tax calculator with a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);
        } else {
            // Instantiate a new tax calculator without a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status);
        }

        double netTaxRate = 0.00;
        if (grossIncome != 0) {
            netTaxRate = (taxDue) / grossIncome;
        }
        // Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Now check the taxable income.
        assertEquals(calculator.getNetTaxRate(), netTaxRate, 0.01);
    }

    @Test(dataProvider = "taxableIncomeDataProvider")
    public void testGetTaxDue(int status, int age, int spouseAge, double grossIncome, double taxableIncome,
                              double taxDue) {
        if (spouseAge != -1) {
            // Instantiate a new tax calculator with a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);
        } else {
            // Instantiate a new tax calculator without a spouse.
            calculator = new TaxCalculator("Bob Smith", age, status);
        }

        // Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Now check the taxable income.
        assertEquals(calculator.getTaxDue(), taxDue, 0.01);
    }

}
