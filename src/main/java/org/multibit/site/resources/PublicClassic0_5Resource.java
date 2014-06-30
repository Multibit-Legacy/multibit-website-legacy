package org.multibit.site.resources;

import org.multibit.site.model.BaseModel;
import org.multibit.site.views.PublicFreemarkerView;

import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of bare help from v0.5.x of the MultiBit Classic client</li>
 * </ul>
 *
 * @since 3.0.0
 *         
 */
@Path("/v0.5")
public class PublicClassic0_5Resource extends BaseResource {

  /**
   * Provide the help images
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("/helpImages/{image}")
  public Response getHelpImage(
    @PathParam("image") String image
  ) {

    InputStream is = PublicPageResource.class.getResourceAsStream("/assets/images/en/v0_5/"+image);

    if (is == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    return Response
      .ok(is)
      .type("image/png")
      .build();

  }

  /**
   * Provide the help contents in the default language
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  public PublicFreemarkerView<BaseModel> getDefaultLanguageHelpContents() {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + DEFAULT_LANGUAGE + "/help/v0.5/help_contents.html";

    BaseModel model = new BaseModel(resourcePath);
    return new PublicFreemarkerView<BaseModel>("content/bare-help.ftl", model);

  }

  /**
   * Provide a default language help page
   *
   * @param page The path parameters leading to the required resource
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("{page}.html")
  public PublicFreemarkerView<BaseModel> getLanguageSpecificHelpPage(
    @PathParam("page") String page
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + DEFAULT_LANGUAGE + "/help/v0.5/" + page + ".html";

    BaseModel model = new BaseModel(resourcePath);
    return new PublicFreemarkerView<BaseModel>("content/bare-help.ftl", model);

  }

  /**
   * Provide a language specific help page
   *
   * @param lang      The two letter language code (ISO 639-1)
   * @param pathParam The path parameters leading to the required resource
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Path("{lang}/{pathParam: (?).*}")
  @Produces(MediaType.TEXT_HTML + ";charset=utf-8")
  public PublicFreemarkerView<BaseModel> getLanguageSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("pathParam") String pathParam
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/v0.5/" + pathParam;

    BaseModel model = new BaseModel(resourcePath);
    return new PublicFreemarkerView<BaseModel>("content/bare-help.ftl", model);

  }


}
