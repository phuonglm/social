package org.exoplatform.social.core.test;

import junit.framework.TestCase;
import org.exoplatform.social.core.StringUtils;

/**
 * @author <a href="mailto:alain.defrance@exoplatform.com">Alain Defrance</a>
 */
public class StringUtilsTest extends TestCase {

  public void testRemoveSpecialCharacter() {
    assertEquals("The filter should only filter special characters only","script alert Hello script 100", StringUtils.removeSpecialCharacterInSpaceFilter("<script>alert('Hello');</script> 100"));
    assertEquals("The filter should keep wildcard *,? and %","% * ?", StringUtils.removeSpecialCharacterInSpaceFilter("( ) %{ } * [ ] \'? \""));
    /* I comment this part because unicode String may have problem with Jenkins build.
    assertEquals("The filter should only filter special characters only","script alert a á à ả ã ạ ă ắ ằ ẳ ẵ ặ â" +
    		          " ấ ầ ẩ ẫ ậ o ó ò ỏ õ ọ ơ ớ ờ ở ỡ ợ u ú ù ủ ũ ụ ư ứ ừ ử ữ ự đ script 100",
    		          SpaceUtils.removeSpecialCharacterInSpaceFilter("<script>alert(' a á à ả ã ạ ă ắ ằ ẳ ẵ ặ â" +
                  " ấ ầ ẩ ẫ ậ o ó ò ỏ õ ọ ơ ớ ờ ở ỡ ợ u ú ù ủ ũ ụ ư ứ ừ ử ữ ự đ');</script> 100"));
    assertEquals("The filter should keep the unicode characters","ˆáàâäåÁÃÄÅÀÂæÆçÇêéëèÊËÉÈïíîìÍÌÎÏñÑœŒöòõóøÓÔÕØÖÒšŠúüûùÙÚÜÛÿŸýÝžŽÞþƒßµÐºÇüéâäàåçêëèïîìÄÅÉæÆôöòû" +
    		         "ùÿÖÜáíóúñÑºĈĉĜĝĤĥĴĵŜŝŬŭàâçéèêëîïœôùûÀÂÇÈÉÊËÎÏŒáéíñóúüÁÉÍÑÓÚÜÔÙÛàèìòùÀÈÌÒÙãÃçÇòÒóÓõÕäåæðëöøßþü" +
    		         "ÿÄÅÆÐËÖØÞÜ",
    		         SpaceUtils.removeSpecialCharacterInSpaceFilter(
		             "ˆáàâäåÁÃÄÅÀÂæÆçÇêéëèÊËÉÈïíîìÍÌÎÏñÑœŒöòõóøÓÔÕØÖÒšŠúüûùÙÚÜÛÿŸýÝžŽÞþƒßµÐºÇüéâäàåçêëèïîìÄÅÉæÆôöòû" +
                 "ùÿÖÜáíóúñÑºĈĉĜĝĤĥĴĵŜŝŬŭàâçéèêëîïœôùûÀÂÇÈÉÊËÎÏŒáéíñóúüÁÉÍÑÓÚÜÔÙÛàèìòùÀÈÌÒÙãÃçÇòÒóÓõÕäåæðëöøßþü" +
                 "ÿÄÅÆÐËÖØÞÜ"
                 ));
    */
  }

}
