package org.exoplatform.social.common;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 */
public enum IdentityType {
  ORGANIZATION,
  SPACE;

  public String string() {
    return toString().toLowerCase();
  }
  
}
