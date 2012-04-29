package payrollcasestudy.transactions.add;

import org.junit.Rule;
import org.junit.Test;
import payrollcasestudy.entities.Employee;
import payrollcasestudy.entities.paymentclassifications.PaymentClassification;
import payrollcasestudy.entities.paymentclassifications.SalariedClassification;
import payrollcasestudy.entities.paymentmethods.HoldMethod;
import payrollcasestudy.entities.paymentschedule.MonthlyPaymentSchedule;
import payrollcasestudy.transactions.DatabaseResource;
import payrollcasestudy.transactions.Transaction;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * Listing 19-2
 */
public class AddSalariedEmployeeTransactionTest {
    protected double FLOATING_POINT_ACCURACY = 0.000001;

    @Rule
    public DatabaseResource databaseResource = new DatabaseResource();

    @Test
    public void testAddSalariedEmployee(){
        int employeeId = 1;
        Transaction addEmployeeTransaction =
                new AddSalariedEmployeeTransaction(employeeId, "Bob", "Home", 1000.0);
        addEmployeeTransaction.execute();

        Employee employee = databaseResource.getInstance().getEmployee(employeeId);
        assertThat(employee.getName(), is("Bob"));

        PaymentClassification paymentClassification = employee.getPaymentClassification();
        assertThat(paymentClassification, is(instanceOf(SalariedClassification.class)));
        SalariedClassification salariedClassification = (SalariedClassification) paymentClassification;
        assertThat(salariedClassification.getSalary(), closeTo(1000.0, FLOATING_POINT_ACCURACY));

        assertThat(employee.getPaymentSchedule(), is(instanceOf(MonthlyPaymentSchedule.class)));
        assertThat(employee.getPaymentMethod(), is(instanceOf(HoldMethod.class)));
    }

}