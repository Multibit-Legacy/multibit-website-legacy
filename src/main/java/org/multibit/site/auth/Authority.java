package org.multibit.site.auth;

/**
 * <p>Enumeration to provide the following to application:</p>
 * <ul>
 * <li>Provision of standard authorities grouped by Role</li>
 * </ul>
 * <p>An Authority exists to provide an enum key to be mapped into an RestrictedTo annotation.</p>
 *
 * @since 0.0.4
 *         
 */
public enum Authority {

  // Naming conventions help navigation and avoid duplication
  // An Authority is named as VERB_SUBJECT_ENTITY
  // Verbs should initially follow CRUD (CREATE, RETRIEVE, UPDATE, DELETE)
  // Subjects are based on outward looking relationships (OWN, OTHERS)
  // Entities are based on primary entities (USER, CUSTOMER, CART, ITEM, INVOICE)

  // Roles (act as EnumSets from the fine grained authorities defined later)

  // External roles
  /**
   * An anonymous (public) customer
   */
  ROLE_PUBLIC(false),

  // Granted authorities
  // Users
  ACCEPT_TERMS_AND_CONDITIONS(false)

  // End of enum
  ;

  /**
   * True if the authority can only be applied to an internal User (staff)
   */
  private boolean internal = false;

  /**
   * @param internal True if the authority is appropriate for staff only
   */
  Authority(boolean internal) {
    this.internal = internal;
  }

  /**
   * @return True if this Authority can be applied to internal Users only, false means both
   */
  public boolean isInternal() {
    return internal;
  }

}

