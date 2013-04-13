package org.multibit.site.resources;

import org.multibit.site.model.BaseModel;
import org.multibit.site.views.PublicFreemarkerView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
public class PublicHomeResource extends BaseResource {

  @GET
  public PublicFreemarkerView<BaseModel> getIndex() {

    // Provide a default
    return getHome("/en/index.html");

  }

  @GET
  @Path("/{resourcePath: (?!images|css).*}")
  public PublicFreemarkerView<BaseModel> getHome(
    @PathParam("resourcePath") String resourcePath
  ) {

    // TODO Implement i18n
    if (!resourcePath.startsWith("/en/")) {
      resourcePath = "/en/" + resourcePath;
    } else {
      resourcePath = "/" + resourcePath;
    }

    BaseModel model = new BaseModel(resourcePath);
    return new PublicFreemarkerView<BaseModel>("content/home.ftl", model);

  }

}
