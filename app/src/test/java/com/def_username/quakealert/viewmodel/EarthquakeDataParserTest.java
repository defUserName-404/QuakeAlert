package com.def_username.quakealert.viewmodel;

import com.def_username.quakealert.util.EarthquakeDataParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class EarthquakeDataParserTest {

	private static final String json = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1646908853000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&starttime=03-03-2022&endtime=03-04-2022&minmagnitude=6\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.13.3\",\"count\":2},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":6.1,\"place\":\"Fiji region\",\"time\":1646631258630,\"updated\":1646717921306,\"tz\":null,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us6000h2nu\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us6000h2nu&format=geojson\",\"felt\":1,\"cdi\":1,\"mmi\":2.167,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":573,\"net\":\"us\",\"code\":\"6000h2nu\",\"ids\":\",us6000h2nu,usauto6000h2nu,pt22066000,\",\"sources\":\",us,usauto,pt,\",\"types\":\",dyfi,ground-failure,internal-moment-tensor,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":4.233,\"rms\":1.15,\"gap\":19,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.1 - Fiji region\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-178.4313,-20.3797,581.81]},\"id\":\"us6000h2nu\"},\n" +
			"{\"type\":\"Feature\",\"properties\":{\"mag\":6,\"place\":\"South Sandwich Islands region\",\"time\":1646597670463,\"updated\":1646684316517,\"tz\":null,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us6000h2l5\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us6000h2l5&format=geojson\",\"felt\":1,\"cdi\":1,\"mmi\":3.732,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":554,\"net\":\"us\",\"code\":\"6000h2l5\",\"ids\":\",us6000h2l5,usauto6000h2l5,at00r8cawc,pt22065001,\",\"sources\":\",us,usauto,at,pt,\",\"types\":\",dyfi,internal-moment-tensor,internal-origin,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":7.812,\"rms\":1.51,\"gap\":31,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 6.0 - South Sandwich Islands region\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[-24.8859,-58.7354,15.48]},\"id\":\"us6000h2l5\"}],\"bbox\":[-178.4313,-58.7354,15.48,-24.8859,-20.3797,581.81]}";

	public EarthquakeDataParserTest() {
		try {
			EarthquakeDataParser.setSampleJsonResponse(new JSONObject(json));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void extractEarthquakes() {
	}

	@Test
	public void formatDate() {
	}

	@Test
	public void formatDateForResponse() {
	}

	@Test
	public void formatTime() {
	}

	@Test
	public void formatDecimal() {
	}

	@Test
	public void formatLocation() {
	}

	@Test
	public void getLatitudeLongitudeFromPlaceName() {
	}

	@Test
	public void getBGColor() {
	}

}
