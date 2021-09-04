package com.laraveltestpack.utility;

import com.laraveltestpack.pages.BasePage;
import io.qameta.allure.Allure;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.Status;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.IAssert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.laraveltestpack.utility.StringUtils.exceptionToString;

public class SoftAssert extends org.testng.asserts.SoftAssert  {
    private WebDriver driver;

    public SoftAssert(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void onBeforeAssert(IAssert<?> iAssert) {
        boolean isNot = false;
        boolean isFail = false;

        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : stElements) {
            // Check if the Assert is negative.
            if (ste.getMethodName().startsWith("assert")) {
                String[] methodName = ste.getMethodName().split("(?=[A-Z])");
                isNot = methodName.length > 2 && methodName[1].equals("Not");
                break;
            } else if (ste.getMethodName().equals("fail")) {
                isFail = true;
                break;
            }
        }

        final String uuid = UUID.randomUUID().toString();
        final String message = iAssert.getMessage();

        String name = (isFail ? "[Fail]" : "[Assert]") + (message == null ? "" : " " + message);

        StepResult result = new StepResult().setName(name);
        if (!isFail) {
            result.setParameters(getAssertParameters(iAssert, isNot));
        }
        Allure.getLifecycle().startStep(uuid, result);
        super.onBeforeAssert(iAssert);
    }

    @Override
    public void onAssertFailure(IAssert<?> iAssert, AssertionError assertionError) {
        super.onAssertFailure(iAssert, assertionError);
        new BasePage(driver).takeScreenshot("Assertion");

        Throwable cause = assertionError.getCause();
        if (cause != null) {
            Allure.attachment("Original Exception", exceptionToString(cause));
        }

        Allure.attachment("Assertion Exception", exceptionToString(assertionError));

        Allure.getLifecycle().updateStep(s -> s
                .setStatus(ResultsUtils.getStatus(assertionError).orElse(Status.BROKEN))
                .setStatusDetails(ResultsUtils.getStatusDetails(assertionError).orElse(null)));
        Allure.getLifecycle().stopStep();
    }

    @Override
    public void onAssertSuccess(IAssert<?> iAssert) {
        super.onAssertSuccess(iAssert);
        Allure.getLifecycle().updateStep(s -> s.setStatus(Status.PASSED));
        Allure.getLifecycle().stopStep();
    }

    private List<Parameter> getAssertParameters(IAssert iAssert, boolean isNot) {
        List<Parameter> list = new ArrayList<>();

        Object actual = iAssert.getActual();
        String actualValue;
        if (actual != null && actual.getClass().isArray()) {
            actualValue = Arrays.toString((Object[]) actual);
        } else {
            actualValue = String.valueOf(actual);
        }

        Object expected = iAssert.getExpected();
        String expectedValue;
        if (expected != null && expected.getClass().isArray()) {
            expectedValue = Arrays.toString((Object[]) expected);
        } else {
            expectedValue = String.valueOf(expected);
        }

        list.add(new Parameter()
                .setName("Actual")
                .setValue(actualValue)
        );

        list.add(new Parameter()
                .setName(isNot ? "Expected (Not)" : "Expected")
                .setValue(expectedValue)
        );
        return list;
    }
}
