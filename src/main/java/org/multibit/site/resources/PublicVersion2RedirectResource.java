package org.multibit.site.resources;

import org.multibit.site.model.BaseModel;
import org.multibit.site.views.PublicFreemarkerView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of permanent redirects from v2 of the site to present</li>
 * </ul>
 *
 * @since 3.0.0
 *         
 */
@Path("/v0.4")
public class PublicVersion2RedirectResource extends BaseResource {

  /**
   * Provide the default language blog page
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("/helpImages/{image}")
  public PublicFreemarkerView<BaseModel> redirectHelpImages(
    @PathParam("image") String image
  ) {

    // TODO Implement this with a 301 and correct path
    return null;

  }

}
