package org.multibit.site.views;

import com.google.common.base.Preconditions;
import com.yammer.dropwizard.views.View;
import org.multibit.site.model.BaseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>View to provide the following to resources:</p>
 * <ul>
 * <li>Representation provided by a Freemarker template with a given model</li>
 * </ul>
 *
 * @since 0.0.1
 *
 */
public class PublicFreemarkerView<T extends BaseModel> extends View {

  private static final Logger log = LoggerFactory.getLogger(PublicFreemarkerView.class);

  private final T model;

  public PublicFreemarkerView(String templateName, T model) {
    super("/views/ftl/"+templateName);

    Preconditions.checkNotNull(model, "'model' must be present");

    this.model = model;

    log.debug("Freemarker view created OK");
  }

  public T getModel() {
    return model;
  }
}
