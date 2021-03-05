package com.aembootcamp.core.util;

import static org.apache.sling.testing.mock.sling.ResourceResolverType.RESOURCERESOLVER_MOCK;

import com.adobe.acs.commons.models.injectors.annotation.impl.AemObjectAnnotationProcessorFactory;
import com.adobe.acs.commons.models.injectors.impl.AemObjectInjector;
import com.adobe.acs.commons.util.ModeUtil;
import io.wcm.testing.mock.aem.junit.AemContext;
import io.wcm.testing.mock.aem.junit.AemContextBuilder;
import io.wcm.testing.mock.aem.junit.AemContextCallback;
import org.apache.sling.settings.SlingSettingsService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.cm.ConfigurationException;

/**
 * AemContext for Testing
 */
public class AppAemContext {
  
  /**
   * Method to instantiate AemContext
   *
   * @return object created
   */
  public static AemContext newAemContext() {
    return (new AemContextBuilder(RESOURCERESOLVER_MOCK))
        .afterSetUp(new AfterSetupCallback())
        .build();
  }

  public static AemContext newAemContext(String[] runmodes) {
    return (new AemContextBuilder(RESOURCERESOLVER_MOCK))
        .afterSetUp(new AfterSetupCallback(runmodes))
        .build();
  }

  /**
   * Class used to setup the models for package and load the content for the component.
   */
  private static final class AfterSetupCallback implements AemContextCallback {
    private String[] runmodes = {"author"};

    AfterSetupCallback(String[] runmodes) {
      this.runmodes = runmodes;
    }
    AfterSetupCallback(){}

    @Override
    public void execute(AemContext context) throws ConfigurationException {

      // Register all model classes from model package.
      context.addModelsForPackage("com.aembootcamp.core.models");
      // register AemObject Injector to be used for testing
      context.registerInjectActivateService(new AemObjectAnnotationProcessorFactory());
      context.registerService(AemObjectInjector.class, new AemObjectInjector());

      BundleContext bundleContext = context.bundleContext();
      ServiceReference ref  = bundleContext.getServiceReference(SlingSettingsService.class.getName());
      SlingSettingsService service = (SlingSettingsService) bundleContext.getService(ref);
      try {
        ModeUtil.configure(service);
      } catch (ConfigurationException ex) {
        ex.printStackTrace();
      }
    }
  }
}