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
        calculator = new TaxCalculator("Bob", 21, TaxCalculatorInterface.SINGLE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetFilingStatusMarriedForSingle() {
        calculator = new TaxCalculator("Hingle McCringleberry", 40, TaxCalculator.MARRIED_FILING_JOINTLY);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetFilingStatusSingleForMarried() {
        calculator = new TaxCalculator("Scoish Velociraptor Maloish", 28, TaxCalculator.SINGLE, 27);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetAgeNegative() throws IllegalArgumentException {
        calculator = new TaxCalculator("D'Isiah T. Billings-Clyde", -1, TaxCalculator.SINGLE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSetSpouseAgeNegative() throws IllegalArgumentException {
        calculator = new TaxCalculator("Javaris Jamar Javarison-Lamar", 40, TaxCalculator.MARRIED_FILING_JOINTLY, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNoNameProvidedForSingleFilerShouldThrowException() throws IllegalArgumentException {
        calculator = new TaxCalculator("", 21, TaxCalculatorInterface.SINGLE);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTaxCalculatorWithNegativeAge() {
        calculator = new TaxCalculator("T.J. A.J. R.J. Backslashinfourth V", -2, TaxCalculator.SINGLE);
    }

    @Test
    public void testGetName() {
        // Arrange
        calculator = new TaxCalculator("Jackmerius Tacktheritrix", 24, TaxCalculatorInterface.SINGLE);
        String expectedName = "Jackmerius Tacktheritrix";

        // Act
        String actualName = calculator.getName();

        // Assert
        assertEquals(actualName, expectedName);
    }

    @Test
    public void testGetAge() {
        // Arrange
        calculator = new TaxCalculator("Torque (Construction Noise) Lewith", 22, TaxCalculator.SINGLE);
        int expectedAge = 22;

        // Act
        int actualAge = calculator.getAge();

        // Assert
        assertEquals(actualAge, expectedAge);
    }

    @Test
    public void testGetSpouseAge() {
        // Arrange
        calculator = new TaxCalculator("Xmus Jaxon Flaxon-Waxon", 100, TaxCalculator.MARRIED_FILING_JOINTLY, 100);
        int expectedAge = 100;

        // Act
        int actualAge = calculator.getSpouseAge();

        // Assert
        assertEquals(actualAge, expectedAge);
    }

    @DataProvider(name = "singleTaxFilingStatusDataProvider")
    public Object[][] singleTaxFilingStatusDataProvider() {
        return new Object[][]{
                new Object[]{TaxCalculatorInterface.SINGLE, 30},
                new Object[]{TaxCalculatorInterface.SINGLE, 70},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 40},
                new Object[]{TaxCalculatorInterface.HEAD_OF_HOUSEHOLD, 70},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70}
        };
    }

    @Test(dataProvider = "singleTaxFilingStatusDataProvider")
    public void testFilingStatusShouldBeCorrectFor(int status, int age) {
        calculator = new TaxCalculator("Bob Smith", age, status);
        assertEquals(calculator.getFilingStatus(), status);
    }

    @DataProvider(name = "marriedTaxFilingStatusDataProvider")
    public Object[][] marriedTaxFilingStatusDataProvider() {
        return new Object[][]{
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 66, 63},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 70, 69},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 60},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 65},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 50, 70}
        };
    }

    @Test(dataProvider = "marriedTaxFilingStatusDataProvider")
    public void testStatus(int status, int age, int spouseAge) {
        calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);
        assertEquals(calculator.getFilingStatus(), status);
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
        calculator = new TaxCalculator("Bob Smith", age, status);

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
        // Arrange - Instantiate a new tax calculator.
        calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);

        // Act - Now set the income.
        calculator.setGrossIncome(income);

        // Assert - Now check to see whether the return is required.
        assertEquals(calculator.isReturnRequired(), returnRequired);

    }

    @DataProvider(name = "standardDeductionForSingleStatusesDataProvider")
    public Object[][] standardDeductionForSingleStatusesDataProvider() {
        return new Object[][]{new Object[]{TaxCalculatorInterface.SINGLE, 30, 5450.00},
                new Object[]{TaxCalculatorInterface.SINGLE, 70, 6500.00},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 11950.00},
        };
    }

    @Test(dataProvider = "standardDeductionForSingleStatusesDataProvider")
    public void testStandardDeductionIsCorrectForSingleStatuses(int status, int age, double standardDeduction) {
        // Instantiate a new tax calculator without a spouse.
        calculator = new TaxCalculator("Bob Smith", age, status);

        assertEquals(calculator.getStandardDeduction(), standardDeduction, 1.00);
    }

    @Test
    public void testGetStandardDeductionForHeadOfHousehold() {
        calculator = new TaxCalculator("Saggitariutt Jefferspin", 65, TaxCalculator.HEAD_OF_HOUSEHOLD);

        assertEquals(calculator.getStandardDeduction(), 9050.0);
    }

    @DataProvider(name = "standardDeductionForMarriedStatusesDataProvider")
    public Object[][] standardDeductionForMarriedStatusesDataProvider() {
        return new Object[][]{
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_JOINTLY, 30, 40, 10900.00},
                new Object[]{TaxCalculatorInterface.MARRIED_FILING_SEPARATELY, 70, 70, 6500.00}
        };
    }

    @Test(dataProvider = "standardDeductionForMarriedStatusesDataProvider")
    public void testStandardDeductionIsCorrectForMarriedStatuses(int status, int age, int spouseAge, double standardDeduction) {
        calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);
        assertEquals(calculator.getStandardDeduction(), standardDeduction, 1.00);
    }

    @DataProvider(name = "taxableIncomeForSingleStatusesDataProvider")
    public Object[][] taxableIncomeForSingleStatusesDataProvider() {
        return new Object[][]{
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 5450.00, 0.00, 0},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 5451.00, 1.00, 0.1},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 13474.00, 8024.00, 802.4},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 13475.00, 8025.00, 802.5},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 13476.00, 8026.00, 802.65},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 37999.00, 32549.00, 4481.1},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 38000.00, 32550.00, 4481.25},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 38001.00, 32551.00, 4481.5},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 84299.00, 78849.00, 16056},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 84300.00, 78850.00, 16056.25},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 84301.00, 78851.00, 16056.53},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 169999.00, 164549.00, 40051.97},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 170000.00, 164550.00, 40052.25},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 170001.00, 164551.00, 40052.58},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 363149.00, 357699.00, 103791.42},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 363150.00, 357700.00, 103791.75},
                new Object[]{TaxCalculatorInterface.SINGLE, 30, 363151.00, 357701.00, 103792.1},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 26949.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 26950.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 26951.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 75999.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 76000.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 76001.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 142349.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 142350.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 142351.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 211199.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 211200.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 211201.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 368599.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 368600.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 30, 368601.00, 357701.00, 96770.35},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 27999.00, 16049.00, 1604.9},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 28000.00, 16050.00, 1605},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 28001.00, 16051.00, 1605.15},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 77049.00, 65099.00, 8962.35},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 77050.00, 65100.00, 8962.5},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 77051.00, 65101.00, 8962.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 143399.00, 131449.00, 25549.75},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 143400.00, 131450.00, 25550},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 143401.00, 131451.00, 25550.28},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 212249.00, 200299.00, 44827.72},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 212250.00, 200300.00, 44828},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 212251.00, 200301.00, 44828.33},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 369649.00, 357699.00, 96769.67},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 369650.00, 357700.00, 96770},
                new Object[]{TaxCalculatorInterface.QUALIFYING_WIDOWER, 70, 369651.00, 357701.00, 96770.35},
        };
    }

    @DataProvider(name = "taxableIncomeForMarriedStatusesDataProvider")
    public Object[][] taxableIncomeForMarriedStatusesDataProvider() {
        return new Object[][]{
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

    @Test(dataProvider = "taxableIncomeForSingleStatusesDataProvider")
    public void testTaxableIncomeShouldBeCorrectForSingleStatuses(int status, int age, double grossIncome, double taxableIncome,
                                     double taxDue) {
        // Arrange - Instantiate a new tax calculator
        calculator = new TaxCalculator("Bob Smith", age, status);

        // Act - Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Assert - Now check the taxable income.
        assertEquals(calculator.getTaxableIncome(), taxableIncome);
    }

    @Test(dataProvider = "taxableIncomeForMarriedStatusesDataProvider")
    public void testTaxableIncomeShouldBeCorrectForMarriedStatuses(int status, int age, int spouseAge, double grossIncome, double taxableIncome,
                                     double taxDue) {
        // Arrange - Instantiate a new tax calculator with a spouse.
        calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);

        // Act - Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Assert - Now check the taxable income.
        assertEquals(calculator.getTaxableIncome(), taxableIncome);
    }


    @Test(dataProvider = "taxableIncomeForSingleStatusesDataProvider")
    public void testNetTaxRateShouldBeCorrectForSingleStatuses(int status, int age, double grossIncome, double taxableIncome,
                                  double taxDue) {
        // Arrange - Instantiate a new tax calculator without a spouse.
        calculator = new TaxCalculator("Bob Smith", age, status);

        double netTaxRate = 0.00;
        if (grossIncome != 0) {
            netTaxRate = (taxDue) / grossIncome;
        }
        // Act - Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Assert - Now check the taxable income.
        assertEquals(calculator.getNetTaxRate(), netTaxRate, 0.01);
    }

    @Test(dataProvider = "taxableIncomeForMarriedStatusesDataProvider")
    public void testGetNetTaxRateShouldBeCorrectForMarriedStatuses(int status, int age, int spouseAge, double grossIncome, double taxableIncome,
                                  double taxDue) {
        // Arrange - Instantiate a new tax calculator
        calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);

        double netTaxRate = 0.00;
        if (grossIncome != 0) {
            netTaxRate = (taxDue) / grossIncome;
        }
        // Act - Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Assert - Now check the taxable income.
        assertEquals(calculator.getNetTaxRate(), netTaxRate, 0.01);
    }

    @Test(dataProvider = "taxableIncomeForSingleStatusesDataProvider")
    public void testTaxDueShouldBeCorrectForSingleStatuses(int status, int age, double grossIncome, double taxableIncome,
                              double taxDue) {
        // Arrange - Instantiate a new tax calculator without a spouse.
        calculator = new TaxCalculator("Bob Smith", age, status);

        // Act - Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Assert - Now check the taxable income.
        assertEquals(calculator.getTaxDue(), taxDue, 0.01);
    }

    @Test
    public void testGetTaxDueForHeadOfHousehold() {
        // Arrange
        calculator = new TaxCalculator("Sequester Grundelplith M.D.", 31, TaxCalculator.HEAD_OF_HOUSEHOLD);
        double expectedTaxDue = 0.0;

        // Act
        double taxDue = calculator.getTaxDue();

        // Assert
        assertEquals(taxDue, expectedTaxDue);
    }

    @Test
    public void testGetTaxDueForMarriedFilingSeparately() {
        // Arrange
        calculator = new TaxCalculator("The Player Formerly Known as Mousecop", 31, TaxCalculator.MARRIED_FILING_SEPARATELY, 30);
        double expectedTaxDue = 0.0;

        // Act
        double taxDue = calculator.getTaxDue();

        // Assert
        assertEquals(taxDue, expectedTaxDue);
    }

    @Test(dataProvider = "taxableIncomeForMarriedStatusesDataProvider")
    public void testTaxDueShouldBeCorrectForMarriedStatuses(int status, int age, int spouseAge, double grossIncome, double taxableIncome,
                              double taxDue) {
        // Arrange - Instantiate a new tax calculator with a spouse.
        calculator = new TaxCalculator("Bob Smith", age, status, spouseAge);

        // Act - Setup the gross income.
        calculator.setGrossIncome(grossIncome);

        // Assert - Now check the taxable income.
        assertEquals(calculator.getTaxDue(), taxDue, 0.01);
    }

    @Test
    public void testNetTaxRateWithNegativeGrossIncome() {
        // Arrange
        calculator = new TaxCalculator("D'Glester Hardunkichud", 42, TaxCalculator.SINGLE);

        // Act
        calculator.setGrossIncome(-1);

        // Assert
        assertEquals(calculator.getNetTaxRate(), 0.0);
    }
}
