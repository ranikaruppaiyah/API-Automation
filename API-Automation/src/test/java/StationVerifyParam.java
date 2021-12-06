import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
@RunWith(DataProviderRunner.class)
public class StationVerifyParam {

    @DataProvider
    public static Object[][] origAndName(){
        return new Object[][]{
                {"RICH", "Richmond"},
                {"LAKE", "Lake Merritt"},
                {"NCON", "North Concord/Martinez"},
                {"Cols", "Coliseum"},
        };
    }

    @Test
    @UseDataProvider("origAndName")
    public void testDeparture(String orig, String expectedName){
        String cmdValue = "etd";
        String keyValue = "MW9S-E7SL-26DU-VV8V";

        given().
                queryParam("orig", orig)
                .queryParam("cmd", cmdValue)
                .queryParam("key", keyValue).
                when().
                get("https://api.bart.gov/api/etd.aspx").
                then().
                assertThat().
                body("root.station[0].name", equalTo(expectedName));;
    }
}