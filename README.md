# frontend-maven-plugin

This project is forked from the [original frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin).

I made a modification to the `1.9.1` version to delegate build failure due to failing integration tests until the `verify` phase.
This allows the build to clean up the environment during the `post-integration-test` phase before stopping the build.

This change is included in version `1.9.1-failsafe`. Activate this implementation by:

- Adding the parameter `integrationTestFailureAfterPostIntegration` to the `integration-test` phase configuration and set to **true**
- Adding an execution with `verify` goal

A working example looks like this:

```xml
<execution>
    <id>npm run integration tests</id>
    <goals>
        <goal>npm</goal>
    </goals>
    <phase>integration-test</phase>
    <configuration>
        <arguments>run e2e</arguments>
        <integrationTestFailureAfterPostIntegration>true</integrationTestFailureAfterPostIntegration>
    </configuration>
</execution>
<execution>
    <id>fail any integration tests</id>
    <goals>
        <goal>verify</goal>
    </goals>
    <phase>verify</phase> <!--default phase is verify, so this is optional-->
</execution>
```

Any e2e test that fails will cause the build to fail at the `verify` phase. If all e2e tests
pass then the build will continue.