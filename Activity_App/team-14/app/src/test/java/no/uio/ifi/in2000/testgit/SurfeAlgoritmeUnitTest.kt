package no.uio.ifi.in2000.testgit

import kotlinx.coroutines.runBlocking
import no.uio.ifi.in2000.testgit.data.algorithms.surfeAlgoritme
import no.uio.ifi.in2000.testgit.model.nowcast.Details
import no.uio.ifi.in2000.testgit.model.oceanforecast.OceanDetails
import no.uio.ifi.in2000.testgit.ui.Activity.NowCastUIState
import no.uio.ifi.in2000.testgit.ui.map.OceanForeCastUIState
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


// Four tests to check correctness of algorithm based on our defined
// weather factor limits
@RunWith(JUnit4::class)
class SurfeAlgoritmeUnitTest {

    @Test
    fun testSurfeAlgoritmeHighConditions() {
        // creating dummy data for tests
        val oceanForeCastUIState =
            OceanForeCastUIState(oceanDetails = OceanDetails(2.0, 2.0, 2.0, 12.0, 0.0))
        val nowCastUIState =
            NowCastUIState(nowCastData = Details(20.0, 0.0, 0.0, 0.0, 0.0, 8.0, 8.0))


        val result = runBlocking {
            surfeAlgoritme(oceanForeCastUIState, nowCastUIState)
        }

        // Verifying the expected result
        Assert.assertEquals(8, result)
    }

    @Test
    fun testSurfeAlgoritmeOutOfRangeWindSpeed() {
        // creating dummy data for tests, with wind speed over max limit of 11.1 ms
        val oceanForeCastUIState =
            OceanForeCastUIState(oceanDetails = OceanDetails(2.0, 2.0, 2.0, 12.0, 0.0))
        val nowCastUIState =
            NowCastUIState(nowCastData = Details(20.0, 0.0, 0.0, 0.0, 0.0, 20.0, 8.0))


        val result = runBlocking {
            surfeAlgoritme(oceanForeCastUIState, nowCastUIState)
        }

        // Verifying the expected result, result should return 0 as wind speed is
        // dangerous and out of range from our limits
        Assert.assertEquals(0, result)
    }

    @Test
    fun testSurfeAlgoritmeOutOfRangeWaveHeight() {
        // creating dummy data for tests, with wave over max limit of 2.5
        val oceanForeCastUIState =
            OceanForeCastUIState(oceanDetails = OceanDetails(2.0, 7.0, 2.0, 12.0, 0.0))
        val nowCastUIState =
            NowCastUIState(nowCastData = Details(20.0, 0.0, 0.0, 0.0, 0.0, 8.0, 8.0))


        val result = runBlocking {
            surfeAlgoritme(oceanForeCastUIState, nowCastUIState)
        }

        // Verifying the expected result, result should return 0 as wave height is
        // dangerous and out of range from our limits
        Assert.assertEquals(0, result)
    }

    @Test
    fun testSurfeAlgoritmeOutOfRangeWaterTemp() {
        // creating dummy data for tests, with wave over max limit of 2.5
        val oceanForeCastUIState =
            OceanForeCastUIState(oceanDetails = OceanDetails(2.0, 2.0, 2.0, -4.0, 0.0))
        val nowCastUIState =
            NowCastUIState(nowCastData = Details(20.0, 0.0, 0.0, 0.0, 0.0, 8.0, 8.0))


        val result = runBlocking {
            surfeAlgoritme(oceanForeCastUIState, nowCastUIState)
        }

        // Verifying the expected result, result should return 0 as water temperature is
        // out of range from our limits
        Assert.assertEquals(0, result)
    }

}

