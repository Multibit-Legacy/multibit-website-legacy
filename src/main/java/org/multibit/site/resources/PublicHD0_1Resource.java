package org.multibit.site.resources;

import org.multibit.site.model.BaseModel;

import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Locale;

/**
 * <p>Resource to provide the following to application:</p>
 * <ul>
 * <li>Provision of bare help from v0.1.x of the MultiBit HD client</li>
 * </ul>
 *
 * @since 4.0.0
 *         
 */
@Path("/hd0.1")
public class PublicHD0_1Resource extends BaseResource {

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

    InputStream is = PublicPageResource.class.getResourceAsStream("/assets/images/en/hd0_1/"+image);

    if (is == null) {
      throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

    return applyHeaders(Response
      .ok(is)
      .type("image/png")
    ).build();

  }

  /**
   * Provide the help contents in the default language
   *
   * @return The view (template + data) allowing the HTML to be rendered
   */
  @GET
  @Produces("text/html")
  public Response getDefaultLanguageHelpContents() {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + ENGLISH + "/help/hd0.1/contents.html";

    BaseModel model = new BaseModel(resourcePath, acceptedTandC(), Locale.ENGLISH, getBannerPrefix());

    return pageResponse(model, "content/hd-help.ftl");

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
  public Response getDefaultLanguageHelpPage(
    @PathParam("page") String page
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + ENGLISH + "/help/hd0.1/" + page + ".html";

    BaseModel model = new BaseModel(resourcePath, acceptedTandC(), Locale.ENGLISH, getBannerPrefix());

    return pageResponse(model, "content/bare-hd-help.ftl");

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
  public Response getLanguageSpecificHelpPage(
    @Size(min = 3, max = 3) @PathParam("lang") String lang,
    @PathParam("pathParam") String pathParam
  ) {

    // Java6 uses StringBuilder to optimise this
    String resourcePath = "/" + lang + "/help/hd0.1/" + pathParam;

    BaseModel model = new BaseModel(resourcePath, acceptedTandC(), new Locale(lang), getBannerPrefix());

    return pageResponse(model, "content/bare-hd-help.ftl");

  }

}
