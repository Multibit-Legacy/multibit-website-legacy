package org.multibit.site.resources;

import org.multibit.site.model.BaseModel;
import org.multibit.site.views.PublicFreemarkerView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of a static HTML page</li>
 * </ul>
 *
 * @since 2.0.0
 *         
 */
@Path("/")
public class PublicHomeResource {

  @GET
  public PublicFreemarkerView<BaseModel> getIndex() {

    BaseModel model = new BaseModel();
    return new PublicFreemarkerView<BaseModel>("common/home.ftl",model);

  }


}
