package com.kts.sigma;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.kts.sigma.e2e.tests.BartenderTest;
import com.kts.sigma.e2e.tests.CookTest;
import com.kts.sigma.e2e.tests.FoodDrinksTest;
import com.kts.sigma.e2e.tests.LoginTest;
import com.kts.sigma.e2e.tests.PeopleTest;
import com.kts.sigma.e2e.tests.ReportsTest;
import com.kts.sigma.e2e.tests.WaiterTablesTest;
import com.kts.sigma.e2e.tests.ZonesTest;



@RunWith(Suite.class)
@Suite.SuiteClasses({
	BartenderTest.class,
	CookTest.class,
	FoodDrinksTest.class,
	LoginTest.class,
	PeopleTest.class,
	ReportsTest.class,
	WaiterTablesTest.class,
	ZonesTest.class
})
public class TestSuiteE2E {

}
