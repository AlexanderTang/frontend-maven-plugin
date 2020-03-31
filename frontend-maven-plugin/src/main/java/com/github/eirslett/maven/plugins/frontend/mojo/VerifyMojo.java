package com.github.eirslett.maven.plugins.frontend.mojo;

import com.github.eirslett.maven.plugins.frontend.lib.FrontendPluginFactory;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name="verify",  defaultPhase = LifecyclePhase.VERIFY, threadSafe = true)
public class VerifyMojo extends AbstractFrontendMojo {

    static final String INTEGRATION_TEST_FAILED_KEY = "integrationTestFailed";

    @Override
    protected boolean skipExecution() {
        return false;
    }

    @Override
    protected synchronized void execute(FrontendPluginFactory factory) throws MojoFailureException {
        if(integrationTestsHaveFailed()){
            throw new MojoFailureException("Some integration tests have failed during integration-test phase.");
        }
    }

    private Boolean integrationTestsHaveFailed() {
        Object failed = getPluginContext().get(INTEGRATION_TEST_FAILED_KEY);
        return failed != null && (Boolean) failed;
    }
}
