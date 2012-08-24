/*
 * Copyright (C) 2003-2011 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.exoplatform.social.core.space.spi;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import junit.framework.Assert;
import org.apache.commons.lang.ArrayUtils;
import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.commons.utils.PageList;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.User;
import org.exoplatform.social.common.lifecycle.LifeCycleCompletionService;
import org.exoplatform.social.core.StringUtils;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.provider.OrganizationIdentityProvider;
import org.exoplatform.social.core.identity.provider.SpaceIdentityProvider;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.model.AvatarAttachment;
import org.exoplatform.social.core.service.LinkProvider;
import org.exoplatform.social.core.space.SpaceException;
import org.exoplatform.social.core.space.SpaceFilter;
import org.exoplatform.social.core.space.SpaceUtils;
import org.exoplatform.social.core.space.impl.DefaultSpaceApplicationHandler;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.storage.api.IdentityStorage;
import org.exoplatform.social.core.test.AbstractAPI1xTest;

public class SpaceServiceTest extends AbstractAPI1xTest {
  private SpaceService spaceService;
  private IdentityStorage identityStorage;
  private LifeCycleCompletionService lifeCycleCompletionService;
  private List<Space> tearDownSpaceList;
  private List<Identity> tearDownUserList;

  private final Log       LOG = ExoLogger.getLogger(SpaceServiceTest.class);

  private Identity demo;
  private Identity tom;
  private Identity raul;
  private Identity ghost;
  private Identity dragon;
  private Identity register1;
  private Identity john;
  private Identity mary;
  private Identity harry;
  private Identity root;
  private Identity jame;
  private Identity paul;
  private Identity hacker;
  private Identity hearBreaker;
  private Identity newInvitedUser;
  private Identity newPendingUser;
  private Identity user_new;
  private Identity user_new1;
  private Identity user_new_dot;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    spaceService = (SpaceService) getContainer().getComponentInstanceOfType(SpaceService.class);
    identityStorage = (IdentityStorage) getContainer().getComponentInstanceOfType(IdentityStorage.class);
    lifeCycleCompletionService = (LifeCycleCompletionService) getContainer().getComponentInstanceOfType(LifeCycleCompletionService.class);
    tearDownSpaceList = new ArrayList<Space>();
    tearDownUserList = new ArrayList<Identity>();
    
    user_new = new Identity(OrganizationIdentityProvider.NAME, "user-new");
    user_new1 = new Identity(OrganizationIdentityProvider.NAME, "user-new.1");
    user_new_dot = new Identity(OrganizationIdentityProvider.NAME, "user.new");
    demo = new Identity(OrganizationIdentityProvider.NAME, "demo");
    tom = new Identity(OrganizationIdentityProvider.NAME, "tom");
    raul = new Identity(OrganizationIdentityProvider.NAME, "raul");
    ghost = new Identity(OrganizationIdentityProvider.NAME, "ghost");
    dragon = new Identity(OrganizationIdentityProvider.NAME, "dragon");
    register1 = new Identity(OrganizationIdentityProvider.NAME, "register1");
    mary = new Identity(OrganizationIdentityProvider.NAME, "mary");
    john = new Identity(OrganizationIdentityProvider.NAME, "john");
    harry = new Identity(OrganizationIdentityProvider.NAME, "harry");
    root = new Identity(OrganizationIdentityProvider.NAME, "root");
    jame = new Identity(OrganizationIdentityProvider.NAME, "jame");
    paul = new Identity(OrganizationIdentityProvider.NAME, "paul");
    hacker = new Identity(OrganizationIdentityProvider.NAME, "hacker");
    hearBreaker = new Identity(OrganizationIdentityProvider.NAME, "hearBreaker");
    newInvitedUser = new Identity(OrganizationIdentityProvider.NAME, "newInvitedUser");
    newPendingUser = new Identity(OrganizationIdentityProvider.NAME, "newPendingUser");

    identityStorage.saveIdentity(demo);
    identityStorage.saveIdentity(tom);
    identityStorage.saveIdentity(raul);
    identityStorage.saveIdentity(ghost);
    identityStorage.saveIdentity(dragon);
    identityStorage.saveIdentity(register1);
    identityStorage.saveIdentity(mary);
    identityStorage.saveIdentity(harry);
    identityStorage.saveIdentity(john);
    identityStorage.saveIdentity(root);
    identityStorage.saveIdentity(jame);
    identityStorage.saveIdentity(paul);
    identityStorage.saveIdentity(hacker);
    identityStorage.saveIdentity(hearBreaker);
    identityStorage.saveIdentity(newInvitedUser);
    identityStorage.saveIdentity(newPendingUser);
    identityStorage.saveIdentity(user_new1);
    identityStorage.saveIdentity(user_new);
    identityStorage.saveIdentity(user_new_dot);

    tearDownUserList = new ArrayList<Identity>();
    tearDownUserList.add(demo);
    tearDownUserList.add(tom);
    tearDownUserList.add(raul);
    tearDownUserList.add(ghost);
    tearDownUserList.add(dragon);
    tearDownUserList.add(register1);
    tearDownUserList.add(mary);
    tearDownUserList.add(harry);
    tearDownUserList.add(john);
    tearDownUserList.add(root);
    tearDownUserList.add(jame);
    tearDownUserList.add(paul);
    tearDownUserList.add(hacker);
    tearDownUserList.add(hearBreaker);
    tearDownUserList.add(newInvitedUser);
    tearDownUserList.add(newPendingUser);
    tearDownUserList.add(user_new1);
    tearDownUserList.add(user_new);
    tearDownUserList.add(user_new_dot);
  }

  @Override
  public void tearDown() throws Exception {
    end();
    begin();

    for (Identity identity : tearDownUserList) {
      identityStorage.deleteIdentity(identity);
    }
    for (Space space : tearDownSpaceList) {
      Identity spaceIdentity = identityStorage.findIdentity(SpaceIdentityProvider.NAME, space.getPrettyName());
      if (spaceIdentity != null) {
        identityStorage.deleteIdentity(spaceIdentity);
      }
      spaceService.deleteSpace(space);
    }
    super.tearDown();
  }

  /**
   * Test {@link SpaceService#getAllSpaces()}
   *
   * @throws Exception
   */
  public void testGetAllSpaces() throws Exception {
    tearDownSpaceList.add(populateData());
    tearDownSpaceList.add(createMoreSpace("Space2"));
    assertEquals(2, spaceService.getAllSpaces().size());
  }

  /**
   * Test {@link SpaceService#getAllSpacesWithListAccess()}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAllSpacesWithListAccess() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> allSpaces = spaceService.getAllSpacesWithListAccess();
    Assert.assertNotNull("allSpaces must not be null", allSpaces);
    Assert.assertEquals("allSpaces.getSize() must return: " + count, count, allSpaces.getSize());
    Assert.assertEquals("allSpaces.load(0, 1).length must return: 1", 1, allSpaces.load(0, 1).length);
    Assert.assertEquals("allSpaces.load(0, count).length must return: " + count, count, allSpaces.load(0, count).length);
  }

  /**
   * Test {@link SpaceService#getSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSpacesByUserId() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    List<Space> memberSpaces = spaceService.getSpaces("raul");
    Assert.assertNotNull("memberSpaces must not be null", memberSpaces);
    Assert.assertEquals("memberSpaces.size() must return: " + count, count, memberSpaces.size());

    memberSpaces = spaceService.getSpaces("ghost");
    Assert.assertNotNull("memberSpaces must not be null", memberSpaces);
    Assert.assertEquals("memberSpaces.size() must return: " + count, count, memberSpaces.size());

    memberSpaces = spaceService.getSpaces("dragon");
    Assert.assertNotNull("memberSpaces must not be null", memberSpaces);
    Assert.assertEquals("memberSpaces.size() must return: " + count, count, memberSpaces.size());

    memberSpaces = spaceService.getSpaces("nobody");
    Assert.assertNotNull("memberSpaces must not be null", memberSpaces);
    Assert.assertEquals("memberSpaces.size() must return: " + 0, 0, memberSpaces.size());
  }

  /**
   * Test {@link SpaceService#getSpaceByDisplayName(String)}
   *
   * @throws Exception
   */
  public void testGetSpaceByDisplayName() throws Exception {
    Space space = populateData();
    tearDownSpaceList.add(space);
    Space gotSpace1 = spaceService.getSpaceByDisplayName("Space1");

    Assert.assertNotNull("gotSpace1 must not be null", gotSpace1);

    Assert.assertEquals(space.getDisplayName(), gotSpace1.getDisplayName());
  }

  public void testGetSpaceByName() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    Space foundSpace = spaceService.getSpaceByName("my_space_10");
    Assert.assertNotNull("foundSpace must not be null", foundSpace);
    Assert.assertEquals("foundSpace.getDisplayName() must return: my space 10", "my space 10", foundSpace.getDisplayName());
    Assert.assertEquals("foundSpace.getPrettyName() must return: my_space_10", "my_space_10", foundSpace.getPrettyName());

    foundSpace = spaceService.getSpaceByName("my_space_0");
    Assert.assertNotNull("foundSpace must not be null", foundSpace);
    Assert.assertEquals("foundSpace.getDisplayName() must return: my space 0", "my space 0", foundSpace.getDisplayName());
    Assert.assertEquals("foundSpace.getPrettyName() must return: my_space_0", "my_space_0", foundSpace.getPrettyName());

    foundSpace = spaceService.getSpaceByName("my_space_20");
    Assert.assertNull("foundSpace must be null", foundSpace);
  }

  /**
   * Test {@link SpaceService#getSpaceByPrettyName(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSpaceByPrettyName() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    Space foundSpace = spaceService.getSpaceByPrettyName("my_space_10");
    Assert.assertNotNull("foundSpace must not be null", foundSpace);
    Assert.assertEquals("foundSpace.getDisplayName() must return: my space 10", "my space 10", foundSpace.getDisplayName());
    Assert.assertEquals("foundSpace.getPrettyName() must return: my_space_10", "my_space_10", foundSpace.getPrettyName());

    foundSpace = spaceService.getSpaceByPrettyName("my_space_0");
    Assert.assertNotNull("foundSpace must not be null", foundSpace);
    Assert.assertEquals("foundSpace.getDisplayName() must return: my space 0", "my space 0", foundSpace.getDisplayName());
    Assert.assertEquals("foundSpace.getPrettyName() must return: my_space_0", "my_space_0", foundSpace.getPrettyName());

    foundSpace = spaceService.getSpaceByPrettyName("my_space_20");
    Assert.assertNull("foundSpace must be null", foundSpace);
  }

  /**
   * Test {@link SpaceService#getSpacesByFirstCharacterOfName(String)}
   *
   * @throws Exception
   */
  public void testGetSpacesByFirstCharacterOfName() throws Exception {
    tearDownSpaceList.add(populateData());
    tearDownSpaceList.add(createMoreSpace("Space2"));
    assertEquals(2, spaceService.getSpacesByFirstCharacterOfName("S").size());
  }

  /**
   * Test {@link SpaceService#getAllSpacesByFilter(SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAllSpacesByFilterWithFirstCharacterOfSpaceName() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter('m'));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter('M'));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());
    Assert.assertEquals("foundSpaceListAccess.load(0, 1).length must return: 1", 1, foundSpaceListAccess.load(0, 1).length);
    Assert.assertEquals("foundSpaceListAccess.load(0, count).length must return: " + count,
        count, foundSpaceListAccess.load(0, count).length);
    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter('H'));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + 0, 0, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter('k'));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + 0, 0, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter('*'));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + 0, 0, foundSpaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#getSpacesBySearchCondition(String)}
   *
   * @throws Exception
   */
  public void testGetSpacesBySearchCondition() throws Exception {
    tearDownSpaceList.add(populateData());
    tearDownSpaceList.add(createMoreSpace("Space2"));
    assertEquals(2, spaceService.getSpacesBySearchCondition("Space").size());
    assertEquals(1, spaceService.getSpacesBySearchCondition("1").size());
  }

  /**
   * Test {@link SpaceService#getAllSpacesByFilter(SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAllSpacesByFilterWithSpaceNameSearchCondition() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }

    ListAccess<Space> foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("my space"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());
    Assert.assertEquals("foundSpaceListAccess.load(0, 1).length must return: 1", 1, foundSpaceListAccess.load(0, 1).length);
    Assert.assertEquals("foundSpaceListAccess.load(0, count).length must return: " + count,
        count, foundSpaceListAccess.load(0, count).length);

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("1"));
    Assert.assertEquals("foundSpaceListAccess.getSize() must return 11", 11, foundSpaceListAccess.getSize());
    Assert.assertEquals("foundSpaceListAccess.load(0, 10).length must return 10",
        10, foundSpaceListAccess.load(0, 10).length);

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("add new space"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("space"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("*space"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("*space*"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("*a*e*"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("*a*e"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("a*e"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("a*"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("%a%e%"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("%a*e%"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("%a*e*"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("***"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + 0, 0, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("%%%%%"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + 0, 0, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("new"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());
    
    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("<new>new(\"new\")</new>"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + count, count, foundSpaceListAccess.getSize());

    foundSpaceListAccess = spaceService.getAllSpacesByFilter(new SpaceFilter("what new space add"));
    Assert.assertNotNull("foundSpaceListAccess must not be null", foundSpaceListAccess);
    Assert.assertEquals("foundSpaceListAccess.getSize() must return: " + 0, 0, foundSpaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#getSpaceByGroupId(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSpaceByGroupId() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    Space foundSpace = spaceService.getSpaceByGroupId("/space/space0");
    Assert.assertNotNull("foundSpace must not be null", foundSpace);
    Assert.assertEquals("foundSpace.getDisplayName() must return: my space 0", "my space 0", foundSpace.getDisplayName());
    Assert.assertEquals("foundSpace.getGroupId() must return: /space/space0", "/space/space0", foundSpace.getGroupId());
  }


  /**
   * Test {@link SpaceService#getSpaceById(String)}
   *
   * @throws Exception
   */
  public void testGetSpaceById() throws Exception {
    Space space = populateData();
    tearDownSpaceList.add(space);
    tearDownSpaceList.add(createMoreSpace("Space2"));
    assertEquals(space.getDisplayName(), spaceService.getSpaceById(space.getId()).getDisplayName());
  }

  /**
   * Test {@link SpaceService#getSpaceByUrl(String)}
   *
   * @throws Exception
   */
  public void testGetSpaceByUrl() throws Exception {
    Space space = populateData();
    tearDownSpaceList.add(space);
    assertEquals(space.getDisplayName(), spaceService.getSpaceByUrl("space1").getDisplayName());
  }

  /**
   * Test {@link SpaceService#getEditableSpaces(String)}
   *
   * @throws Exception
   */
  public void testGetEditableSpaces() throws Exception {
    tearDownSpaceList.add(populateData());
    assertEquals(1, spaceService.getEditableSpaces("root").size());
  }

  /**
   * Test {@link SpaceService#getSettingableSpaces(String))}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSettingableSpaces() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> editableSpaceListAccess = spaceService.getSettingableSpaces("demo");
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());
    Assert.assertEquals("editableSpaceListAccess.load(0, 1).length must return: 1",
        1, editableSpaceListAccess.load(0, 1).length);
    Assert.assertEquals("editableSpaceListAccess.load(0, count).length must return: " + count,
        count, editableSpaceListAccess.load(0, count).length);

    editableSpaceListAccess = spaceService.getSettingableSpaces("tom");
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingableSpaces("root");
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingableSpaces("raul");
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + 0, 0, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingableSpaces("ghost");
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + 0, 0, editableSpaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#getSettingabledSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSettingableSpacesByFilter() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("demo", new SpaceFilter("add"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());
    Assert.assertEquals("editableSpaceListAccess.load(0, 1).length must return: 1",
        1, editableSpaceListAccess.load(0, 1).length);
    Assert.assertEquals("editableSpaceListAccess.load(0, count).length must return: " + count,
        count, editableSpaceListAccess.load(0, count).length);
    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter(demo.getRemoteId(), new SpaceFilter("19"));
    Assert.assertEquals("editableSpaceListAccess.getSize() must return 1", 1, editableSpaceListAccess.getSize());
    Assert.assertEquals("editableSpaceListAccess.load(0, 1).length must return 1",
        1, editableSpaceListAccess.load(0, 1).length);

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("demo", new SpaceFilter("my"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("demo", new SpaceFilter("new"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("demo", new SpaceFilter('m'));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("demo", new SpaceFilter('M'));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("demo", new SpaceFilter('k'));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + 0, 0, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("tom", new SpaceFilter("new"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("root", new SpaceFilter("space"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + count, count, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("raul", new SpaceFilter("my"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + 0, 0, editableSpaceListAccess.getSize());

    editableSpaceListAccess = spaceService.getSettingabledSpacesByFilter("ghost", new SpaceFilter("space"));
    Assert.assertNotNull("editableSpaceListAccess must not be null", editableSpaceListAccess);
    Assert.assertEquals("editableSpaceListAccess.getSize() must return: " + 0, 0, editableSpaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#getInvitedSpaces(String)}
   *
   * @throws Exception
   */
  public void testGetInvitedSpaces() throws Exception {
    tearDownSpaceList.add(populateData());
    assertEquals(0, spaceService.getInvitedSpaces("root").size());
    Space space = spaceService.getSpaceByDisplayName("Space1");
    spaceService.inviteMember(space, "root");
    assertEquals(1, spaceService.getInvitedSpaces("root").size());
  }

  /**
   * Test {@link SpaceService#getInvitedSpacesWithListAccess(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetInvitedSpacesWithListAccess() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> invitedSpaces = spaceService.getInvitedSpacesWithListAccess("register1");
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());
    Assert.assertEquals("invitedSpaces.load(0, 1).length must return: " + 1, 1, invitedSpaces.load(0, 1).length);
    Assert.assertEquals("invitedSpaces.load(0, count).length must return: " + count,
        count, invitedSpaces.load(0, count).length);
    invitedSpaces = spaceService.getInvitedSpacesWithListAccess("mary");
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesWithListAccess("demo");
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

  }

  /**
   * Test {@link SpaceService#getInvitedSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetInvitedSpacesByFilterWithSpaceNameSearchCondition() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> invitedSpaces = spaceService.getInvitedSpacesByFilter("register1", new SpaceFilter("my space"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter(register1.getRemoteId(), new SpaceFilter("12"));
    Assert.assertEquals("invitedSpaces.getSize() must return 1", 1, invitedSpaces.getSize());
    Assert.assertEquals("invitedSpaces.load(0, 1).length must return 1", 1, invitedSpaces.load(0, 1).length);

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("my"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());
    Assert.assertEquals("invitedSpaces.load(0, 1).length must return: 1",
        1, invitedSpaces.load(0, 1).length);
    Assert.assertEquals("invitedSpaces.load(0, count).length must return: " + count,
        count, invitedSpaces.load(0, count).length);

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("*my"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("*my*"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("*my*e*"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("%my%e%"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("%my%e"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("%my*e%"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("%my*e*"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("****"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter("%%%%%"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("demo", new SpaceFilter("my space"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("demo", new SpaceFilter("add new"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("john", new SpaceFilter("space"));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getInvitedSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetInvitedSpacesByFilterWithFirstCharacterOfSpaceName() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> invitedSpaces = spaceService.getInvitedSpacesByFilter("register1", new SpaceFilter('m'));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());
    Assert.assertEquals("invitedSpaces.load(0, 1).length must return: 1", 1, invitedSpaces.load(0, 1).length);
    Assert.assertEquals("invitedSpaces.load(0, count).length must return: " + count,
        count, invitedSpaces.load(0, count).length);
    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter('M'));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + count, count, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("mary", new SpaceFilter('H'));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("demo", new SpaceFilter('m'));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());

    invitedSpaces = spaceService.getInvitedSpacesByFilter("john", new SpaceFilter('M'));
    Assert.assertNotNull("invitedSpaces must not be null", invitedSpaces);
    Assert.assertEquals("invitedSpaces.getSize() must return: " + 0, 0, invitedSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getPublicSpaces(String)}
   *
   * @throws Exception
   */
  public void testGetPublicSpaces() throws Exception {
    tearDownSpaceList.add(populateData());
    assertEquals(0, spaceService.getPublicSpaces("root").size());
  }

  /**
   * Test {@link SpaceService#getPublicSpacesWithListAccess(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPublicSpacesWithListAccess() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> foundSpaces = spaceService.getPublicSpacesWithListAccess("tom");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesWithListAccess("hacker");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesWithListAccess("mary");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesWithListAccess("root");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesWithListAccess("nobody");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 20", count, foundSpaces.getSize());
    Assert.assertEquals("foundSpaces.load(0, 1).length must return: 1", 1, foundSpaces.load(0, 1).length);
    Assert.assertEquals("foundSpaces.load(0, 20).length must return: 20",
        20, foundSpaces.load(0, 20).length);
    foundSpaces = spaceService.getPublicSpacesWithListAccess("bluray");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 20", count, foundSpaces.getSize());
  }


  /**
   * Test {@link SpaceService#getPublicSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPublicSpacesByFilterWithSpaceNameSearchCondition() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    String nameSpace = "my space";
    ListAccess<Space> foundSpaces = spaceService.getPublicSpacesByFilter("tom", new SpaceFilter(nameSpace));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter(nameSpace));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());
    Assert.assertEquals("foundSpaces.load(0, 1).length must return: 1", 1, foundSpaces.load(0, 1).length);
    Assert.assertEquals("foundSpaces.load(0, count).length must return: " + count,
        count, foundSpaces.load(0, count).length);
    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("*m"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("m*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("*my*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("*my*e"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("*my*e*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("%my%e%"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("%my*e%"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("*my%e%"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("***"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter("%%%"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    nameSpace = "my space 1";
    foundSpaces = spaceService.getPublicSpacesByFilter("stranger", new SpaceFilter(""));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    nameSpace = "my space 20";
    foundSpaces = spaceService.getPublicSpacesByFilter("hearBreaker", new SpaceFilter(nameSpace));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getPublicSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPublicSpacesByFilterWithFirstCharacterOfSpaceName() throws Exception {
    int count = 10;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> foundSpaces = spaceService.getPublicSpacesByFilter("stranger", new SpaceFilter('m'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("stranger", new SpaceFilter('M'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("root", new SpaceFilter('M'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("stranger", new SpaceFilter('*'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("tom", new SpaceFilter('M'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("stranger", new SpaceFilter('y'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPublicSpacesByFilter("stranger", new SpaceFilter('H'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    ListAccess<Space> johnPublicSpaces = spaceService.getPublicSpacesByFilter("john", new SpaceFilter('m'));
    Assert.assertEquals("johnPublicSpaces.getSize() must return: 10", 10, johnPublicSpaces.getSize());
    Assert.assertEquals("johnPublicSpaces.load(0, 1).length must return: 1", 1, johnPublicSpaces.load(0, 1).length);
    Space[] johnPublicSpacesArray = johnPublicSpaces.load(0, 10);
    Assert.assertEquals("johnPublicSpaces.load(0, 10).length must return 10", 10, johnPublicSpacesArray.length);
    Assert.assertNotNull("johnPublicSpacesArray[0].getId() must not be null", johnPublicSpacesArray[0].getId());
    Assert.assertNotNull("johnPublicSpacesArray[0].getPrettyName() must not be null",
        johnPublicSpacesArray[0].getPrettyName());
  }

  /**
   * Test {@link SpaceService#getAccessibleSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAccessibleSpaces() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    List<Space> accessibleSpaces = spaceService.getAccessibleSpaces("demo");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.size() must return: " + count, count, accessibleSpaces.size());

    accessibleSpaces = spaceService.getAccessibleSpaces("tom");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.size() must return: " + count, count, accessibleSpaces.size());

    accessibleSpaces = spaceService.getAccessibleSpaces("root");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.size() must return: " + count, count, accessibleSpaces.size());

    accessibleSpaces = spaceService.getAccessibleSpaces("dragon");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.size() must return: " + count, count, accessibleSpaces.size());

    accessibleSpaces = spaceService.getAccessibleSpaces("hellgate");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.size() must return: " + 0, 0, accessibleSpaces.size());
  }

  /**
   * Test {@link SpaceService#getAccessibleSpacesWithListAccess(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAccessibleSpacesWithListAccess() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("demo");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());
    Assert.assertEquals("accessibleSpaces.load(0, 1).length must return: 1", 1, accessibleSpaces.load(0, 1).length);
    Assert.assertEquals("accessibleSpaces.load(0, count).length must return: " + count,
        count, accessibleSpaces.load(0, count).length);

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("tom");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("root");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("dragon");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("ghost");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("raul");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("mary");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesWithListAccess("john");
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getAccessibleSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAccessibleSpacesByFilterWithSpaceNameSearchCondition() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> accessibleSpaces = spaceService.getAccessibleSpacesByFilter("demo", new SpaceFilter("my"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());
    Assert.assertEquals("accessibleSpaces.load(0, 1).length must return: 1", 1, accessibleSpaces.load(0, 1).length);
    Assert.assertEquals("accessibleSpaces.load(0, count).length must return: " + count,
        count, accessibleSpaces.load(0, count).length);

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("tom", new SpaceFilter("space"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("space"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("*space"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("space*"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("*space*"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("*a*e*"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("%a%e%"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("%a*e%"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("%a*e*"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("*****"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("%%%%%%%"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter("add new"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("dragon", new SpaceFilter("my space"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("dragon", new SpaceFilter("add new"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("ghost", new SpaceFilter("my space "));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("hellgate", new SpaceFilter("my space"));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getAccessibleSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetAccessibleSpacesByFilterWithFirstCharacterOfSpaceName() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> accessibleSpaces = spaceService.getAccessibleSpacesByFilter("demo", new SpaceFilter('m'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());
    Assert.assertEquals("accessibleSpaces.load(0, 1).length must return: 1", 1, accessibleSpaces.load(0, 1).length);
    Assert.assertEquals("accessibleSpaces.load(0, count).length must return: " + count,
        count, accessibleSpaces.load(0, count).length);
    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("tom", new SpaceFilter('M'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter('M'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("root", new SpaceFilter('*'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("tom", new SpaceFilter('h'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("dragon", new SpaceFilter('m'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("ghost", new SpaceFilter('M'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + count, count, accessibleSpaces.getSize());

    accessibleSpaces = spaceService.getAccessibleSpacesByFilter("hellgate", new SpaceFilter('m'));
    Assert.assertNotNull("accessibleSpaces must not be null", accessibleSpaces);
    Assert.assertEquals("accessibleSpaces.getSize() must return: " + 0, 0, accessibleSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSpaces() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }

    List<Space> memberSpaceListAccess = spaceService.getSpaces("raul");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.size());

    memberSpaceListAccess = spaceService.getSpaces("ghost");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.size());

    memberSpaceListAccess = spaceService.getSpaces("dragon");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.size());

    memberSpaceListAccess = spaceService.getSpaces("root");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + 0, 0, memberSpaceListAccess.size());
  }

  /**
   * Test {@link SpaceService#getMemberSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetMemberSpaces() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }

    ListAccess<Space> memberSpaceListAccess = spaceService.getMemberSpaces("raul");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());
    Assert.assertEquals("memberSpaceListAccess.load(0, 1).length must return: 1",
        1, memberSpaceListAccess.load(0, 1).length);
    Assert.assertEquals("memberSpaceListAccess.load(0, count).length must return: " + count,
        count, memberSpaceListAccess.load(0, count).length);
    memberSpaceListAccess = spaceService.getMemberSpaces("ghost");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpaces("dragon");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpaces("root");
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + 0, 0, memberSpaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#getMemberSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetMemberSpacesByFilter() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }

    ListAccess<Space> memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter("add"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());
    Assert.assertEquals("memberSpaceListAccess.load(0, 1).length must return: 1",
        1, memberSpaceListAccess.load(0, 1).length);
    Assert.assertEquals("memberSpaceListAccess.load(0, count).length must return: " + count,
        count, memberSpaceListAccess.load(0, count).length);

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter("new"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter("space"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter("my"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter('m'));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter('M'));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("raul", new SpaceFilter('k'));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + 0, 0, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("ghost", new SpaceFilter("my"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("dragon", new SpaceFilter("space"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + count, count, memberSpaceListAccess.getSize());

    memberSpaceListAccess = spaceService.getMemberSpacesByFilter("root", new SpaceFilter("my space"));
    Assert.assertNotNull("memberSpaceListAccess must not be null", memberSpaceListAccess);
    Assert.assertEquals("memberSpaceListAccess.size() must return: " + 0, 0, memberSpaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#getPendingSpaces(String)}
   *
   * @throws Exception
   */
  public void testGetPendingSpaces() throws Exception {
    tearDownSpaceList.add(populateData());
    Space space = spaceService.getSpaceByDisplayName("Space1");
    spaceService.requestJoin(space, "root");
    assertEquals(true, spaceService.isPending(space, "root"));
  }

  /**
   * Test {@link SpaceService#getPendingSpacesWithListAccess(String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPendingSpacesWithListAccess() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> foundSpaces = spaceService.getPendingSpacesWithListAccess("jame");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());
    Assert.assertEquals("foundSpaces.load(0, 1).length must return: 1",
        1, foundSpaces.load(0, 1).length);
    Assert.assertEquals("foundSpaces.load(0, count).length must return: " + count,
        count, foundSpaces.load(0, count).length);

    foundSpaces = spaceService.getPendingSpacesWithListAccess("paul");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesWithListAccess("hacker");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesWithListAccess("ghost");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesWithListAccess("hellgate");
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getPendingSpacesByFilter(String, SpaceFilter))}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPendingSpacesByFilterWithSpaceNameSearchCondition() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    String nameSpace = "my space";
    ListAccess<Space> foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter(nameSpace));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());
    Assert.assertEquals("foundSpaces.load(0, 1).length must return: 1",
        1, foundSpaces.load(0, 1).length);
    Assert.assertEquals("foundSpaces.load(0, count).length must return: " + count,
        count, foundSpaces.load(0, count).length);

    foundSpaces = spaceService.getPendingSpacesByFilter("paul", new SpaceFilter(nameSpace));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("hacker", new SpaceFilter("space"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("add new"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("add*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("*add*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("*add"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("*add*e"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("*add*e*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("%add%e%"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("%add*e%"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("%add*e*"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter("no space"));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#getPendingSpacesByFilter(String, SpaceFilter)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPendingSpacesByFilterWithFirstCharacterOfSpaceName() throws Exception {
    int count = 20;
    for (int i = 0; i < count; i ++) {
      tearDownSpaceList.add(this.getSpaceInstance(i));
    }
    ListAccess<Space> foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter('m'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());
    Assert.assertEquals("foundSpaces.load(0, 1).length must return: 1",
        1, foundSpaces.load(0, 1).length);
    Assert.assertEquals("foundSpaces.load(0, count).length must return: " + count,
        count, foundSpaces.load(0, count).length);

    foundSpaces = spaceService.getPendingSpacesByFilter("paul", new SpaceFilter('M'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + count, count, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter('*'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: " + 0, 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter('H'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());

    foundSpaces = spaceService.getPendingSpacesByFilter("jame", new SpaceFilter('k'));
    Assert.assertNotNull("foundSpaces must not be null", foundSpaces);
    Assert.assertEquals("foundSpaces.getSize() must return: 0", 0, foundSpaces.getSize());
  }

  /**
   * Test {@link SpaceService#createSpace(Space, String)}
   *
   * @throws Exception
   */
  public void testCreateSpace() throws Exception {
    tearDownSpaceList.add(populateData());
    tearDownSpaceList.add(createMoreSpace("Space2"));
    ListAccess<Space> spaceListAccess = spaceService.getAllSpacesWithListAccess();
    Assert.assertNotNull("spaceListAccess must not be null", spaceListAccess);
    Assert.assertEquals("spaceListAccess.getSize() must return: 2", 2, spaceListAccess.getSize());
  }

  /**
   * Test {@link SpaceService#saveSpace(Space, boolean)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testSaveSpace() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    String spaceDisplayName = space.getDisplayName();
    String spaceDescription = space.getDescription();
    String groupId = space.getGroupId();
    Space savedSpace = spaceService.getSpaceByDisplayName(spaceDisplayName);
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertEquals("savedSpace.getDisplayName() must return: " + spaceDisplayName, spaceDisplayName, savedSpace.getDisplayName());
    Assert.assertEquals("savedSpace.getDescription() must return: " + spaceDescription, spaceDescription, savedSpace.getDescription());
    Assert.assertEquals("savedSpace.getGroupId() must return: " + groupId, groupId, savedSpace.getGroupId());
    Assert.assertEquals(null, savedSpace.getAvatarUrl());
  }

  /**
   * Test {@link SpaceService#renameSpace(Space, String)}
   *
   * @throws Exception
   * @since 1.2.8
   */
  public void testRenameSpace() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    
    Identity identity = new Identity(SpaceIdentityProvider.NAME, space.getPrettyName());
    identityStorage.saveIdentity(identity);
    tearDownUserList.add(identity);
    
    String newDisplayName = "new display name";
    
    spaceService.renameSpace(space, newDisplayName);
    
    Space got = spaceService.getSpaceById(space.getId());
    Assert.assertEquals(newDisplayName, got.getDisplayName());
    
    Identity savedIdentity = identityStorage.findIdentity(SpaceIdentityProvider.NAME, space.getPrettyName());
    Assert.assertNotNull(savedIdentity);
  }
  
  /**
   * Test {@link SpaceService#saveSpace(Space, boolean)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testUpdateSpaceAvatar() throws Exception {

    Space space = this.getSpaceInstance(0);
    Identity spaceIdentity = new Identity(SpaceIdentityProvider.NAME, space.getPrettyName());
    identityStorage.saveIdentity(spaceIdentity);

    tearDownSpaceList.add(space);
    tearDownUserList.add(spaceIdentity);

    InputStream inputStream = getClass().getResourceAsStream("/eXo-Social.png");
    AvatarAttachment avatarAttachment =
        new AvatarAttachment(null, "avatar", "png", inputStream, null, System.currentTimeMillis());
    space.setAvatarAttachment(avatarAttachment);

    spaceService.updateSpaceAvatar(space);
    spaceService.updateSpace(space);

    Space savedSpace = spaceService.getSpaceById(space.getId());
    Assert.assertFalse(savedSpace.getAvatarUrl() == null);
    String avatarRandomURL = savedSpace.getAvatarUrl();
    int indexOfRandomVar = avatarRandomURL.indexOf("/?upd=");

    String avatarURL = null;
    if(indexOfRandomVar != -1){
      avatarURL = avatarRandomURL.substring(0,indexOfRandomVar);
    } else {
      avatarURL = avatarRandomURL;
  }
    Assert.assertEquals(LinkProvider.escapeJCRSpecialCharacters(
        String.format(
            "/rest/jcr/repository/portal-test/production/soc:providers/soc:space/soc:%s/soc:profile/soc:avatar",
            space.getPrettyName())
    ), avatarURL);

  }

  /**
   * Test {@link SpaceService#deleteSpace(Space)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testDeleteSpace() throws Exception {
    Space space = this.getSpaceInstance(0);
    String spaceDisplayName = space.getDisplayName();
    String spaceDescription = space.getDescription();
    String groupId = space.getGroupId();
    Space savedSpace = spaceService.getSpaceByDisplayName(spaceDisplayName);
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertEquals("savedSpace.getDisplayName() must return: " + spaceDisplayName, spaceDisplayName, savedSpace.getDisplayName());
    Assert.assertEquals("savedSpace.getDescription() must return: " + spaceDescription, spaceDescription, savedSpace.getDescription());
    Assert.assertEquals("savedSpace.getGroupId() must return: " + groupId, groupId, savedSpace.getGroupId());
    spaceService.deleteSpace(space);
    savedSpace = spaceService.getSpaceByDisplayName(spaceDisplayName);
    Assert.assertNull("savedSpace must be null", savedSpace);
  }

  /**
   * Test {@link SpaceService#updateSpace(Space)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testUpdateSpace() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    String spaceDisplayName = space.getDisplayName();
    String spaceDescription = space.getDescription();
    String groupId = space.getGroupId();
    Space savedSpace = spaceService.getSpaceByDisplayName(spaceDisplayName);
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertEquals("savedSpace.getDisplayName() must return: " + spaceDisplayName, spaceDisplayName, savedSpace.getDisplayName());
    Assert.assertEquals("savedSpace.getDescription() must return: " + spaceDescription, spaceDescription, savedSpace.getDescription());
    Assert.assertEquals("savedSpace.getGroupId() must return: " + groupId, groupId, savedSpace.getGroupId());

    String updateSpaceDisplayName = "update new space display name";
    space.setDisplayName(updateSpaceDisplayName);
    space.setPrettyName(space.getDisplayName());
    spaceService.updateSpace(space);
    savedSpace = spaceService.getSpaceByDisplayName(updateSpaceDisplayName);
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertEquals("savedSpace.getDisplayName() must return: " + updateSpaceDisplayName, updateSpaceDisplayName, savedSpace.getDisplayName());
    Assert.assertEquals("savedSpace.getDescription() must return: " + spaceDescription, spaceDescription, savedSpace.getDescription());
    Assert.assertEquals("savedSpace.getGroupId() must return: " + groupId, groupId, savedSpace.getGroupId());
  }

  /**
   * Test {@link SpaceService#addPendingUser(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testAddPendingUser() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    int pendingUsersCount = space.getPendingUsers().length;
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be false",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
    spaceService.addPendingUser(space, newPendingUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount + 1, pendingUsersCount + 1, space.getPendingUsers().length);
    Assert.assertTrue("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#removePendingUser(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRemovePendingUser() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    int pendingUsersCount = space.getPendingUsers().length;
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be false",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
    spaceService.addPendingUser(space, newPendingUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount + 1,
        pendingUsersCount + 1, space.getPendingUsers().length);
    Assert.assertTrue("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));

    spaceService.removePendingUser(space, newPendingUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount,
        pendingUsersCount, space.getPendingUsers().length);
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#isPendingUser(Space, String)}
   *
   * @throws Exception@since 1.2.0-GA
   * @since 1.2.0-GA
   */
  public void testIsPendingUser() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertTrue("spaceService.isPendingUser(savedSpace, \"jame\") must return true", spaceService.isPendingUser(savedSpace, "jame"));
    Assert.assertTrue("spaceService.isPendingUser(savedSpace, \"paul\") must return true", spaceService.isPendingUser(savedSpace, "paul"));
    Assert.assertTrue("spaceService.isPendingUser(savedSpace, \"hacker\") must return true", spaceService.isPendingUser(savedSpace, "hacker"));
    Assert.assertFalse("spaceService.isPendingUser(savedSpace, \"newpendinguser\") must return false", spaceService.isPendingUser(savedSpace, "newpendinguser"));
  }

  /**
   * Test {@link SpaceService#addInvitedUser(Space, String)}
   *
   * @throws Exception
   *
   */
  public void testAddInvitedUser() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    int invitedUsersCount = savedSpace.getInvitedUsers().length;
    Assert.assertFalse("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return false",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
    spaceService.addInvitedUser(savedSpace, newInvitedUser.getRemoteId());
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getInvitedUsers().length must return: " + invitedUsersCount + 1,
        invitedUsersCount + 1, savedSpace.getInvitedUsers().length);
    Assert.assertTrue("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return true",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#removeInvitedUser(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRemoveInvitedUser() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    int invitedUsersCount = savedSpace.getInvitedUsers().length;
    Assert.assertFalse("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return false",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
    spaceService.addInvitedUser(savedSpace, newInvitedUser.getRemoteId());
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getInvitedUsers().length must return: " + invitedUsersCount + 1,
        invitedUsersCount + 1, savedSpace.getInvitedUsers().length);
    Assert.assertTrue("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return true",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
    spaceService.removeInvitedUser(savedSpace, newInvitedUser.getRemoteId());
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getInvitedUsers().length must return: " + invitedUsersCount,
        invitedUsersCount, savedSpace.getInvitedUsers().length);
    Assert.assertFalse("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return false",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#isInvitedUser(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsInvitedUser() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertTrue("spaceService.isInvitedUser(savedSpace, \"register1\") must return true", spaceService.isInvitedUser(savedSpace, "register1"));
    Assert.assertTrue("spaceService.isInvitedUser(savedSpace, \"mary\") must return true", spaceService.isInvitedUser(savedSpace, "mary"));
    Assert.assertFalse("spaceService.isInvitedUser(savedSpace, \"hacker\") must return false", spaceService.isInvitedUser(savedSpace, "hacker"));
    Assert.assertFalse("spaceService.isInvitedUser(savedSpace, \"nobody\") must return false", spaceService.isInvitedUser(savedSpace, "nobody"));
  }

  /**
   * Test {@link SpaceService#setManager(Space, String, boolean)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testSetManager() throws Exception {
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo", "tom"};
    String[] members = new String[] {"raul", "ghost", "dragon"};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);

    //Space space = this.getSpaceInstance(0);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    int managers = savedSpace.getManagers().length;
    spaceService.setManager(savedSpace, "demo", true);
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getManagers().length must return: " + managers, managers, savedSpace.getManagers().length);

    spaceService.setManager(savedSpace, "john", true);
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getManagers().length must return: " + managers + 1, managers + 1, savedSpace.getManagers().length);

    spaceService.setManager(savedSpace, "demo", false);
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getManagers().length must return: " + managers, managers, savedSpace.getManagers().length);

    // Wait 3 secs to have activity stored
    try {
      IdentityManager identityManager = (IdentityManager) getContainer().getComponentInstanceOfType(IdentityManager.class);
      ActivityManager activityManager = (ActivityManager) getContainer().getComponentInstanceOfType(ActivityManager.class);
      Thread.sleep(3000);
      List<ExoSocialActivity> broadCastActivities = activityManager.getActivities(identityManager.getOrCreateIdentity(SpaceIdentityProvider.NAME, savedSpace.getPrettyName(), false), 0, 10);
      for (ExoSocialActivity activity : broadCastActivities) {
        activityManager.deleteActivity(activity);
      }
    } catch (InterruptedException e) {
      LOG.error(e.getMessage(), e);
    }
  }


  /**
   * Test {@link SpaceService#isManager(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsManager() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertTrue("spaceService.isManager(savedSpace, \"demo\") must return true", spaceService.isManager(savedSpace, "demo"));
    Assert.assertTrue("spaceService.isManager(savedSpace, \"tom\") must return true", spaceService.isManager(savedSpace, "tom"));
    Assert.assertFalse("spaceService.isManager(savedSpace, \"mary\") must return false", spaceService.isManager(savedSpace, "mary"));
    Assert.assertFalse("spaceService.isManager(savedSpace, \"john\") must return false", spaceService.isManager(savedSpace, "john"));
  }

  /**
   * Test {@link SpaceService#isOnlyManager(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsOnlyManager() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertFalse("spaceService.isOnlyManager(savedSpace, \"tom\") must return false", spaceService.isOnlyManager(savedSpace, "tom"));
    Assert.assertFalse("spaceService.isOnlyManager(savedSpace, \"demo\") must return false", spaceService.isOnlyManager(savedSpace, "demo"));

    savedSpace.setManagers(new String[] {"demo"});
    spaceService.updateSpace(savedSpace);
    Assert.assertTrue("spaceService.isOnlyManager(savedSpace, \"demo\") must return true", spaceService.isOnlyManager(savedSpace, "demo"));
    Assert.assertFalse("spaceService.isOnlyManager(savedSpace, \"tom\") must return false", spaceService.isOnlyManager(savedSpace, "tom"));

    savedSpace.setManagers(new String[] {"tom"});
    spaceService.updateSpace(savedSpace);
    Assert.assertFalse("spaceService.isOnlyManager(savedSpace, \"demo\") must return false", spaceService.isOnlyManager(savedSpace, "demo"));
    Assert.assertTrue("spaceService.isOnlyManager(savedSpace, \"tom\") must return true", spaceService.isOnlyManager(savedSpace, "tom"));
  }

  /**
   * Test {@link SpaceService#hasSettingPermission(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testHasSettingPermission() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    Assert.assertTrue("spaceService.hasSettingPermission(savedSpace, \"demo\") must return true", spaceService.hasSettingPermission(savedSpace, "demo"));
    Assert.assertTrue("spaceService.hasSettingPermission(savedSpace, \"tom\") must return true", spaceService.hasSettingPermission(savedSpace, "tom"));
    Assert.assertTrue("spaceService.hasSettingPermission(savedSpace, \"root\") must return true", spaceService.hasSettingPermission(savedSpace, "root"));
    Assert.assertFalse("spaceService.hasSettingPermission(savedSpace, \"mary\") must return false", spaceService.hasSettingPermission(savedSpace, "mary"));
    Assert.assertFalse("spaceService.hasSettingPermission(savedSpace, \"john\") must return false", spaceService.hasSettingPermission(savedSpace, "john"));
  }

  /**
   * Test {@link SpaceService#registerSpaceListenerPlugin(org.exoplatform.social.core.space.SpaceListenerPlugin)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRegisterSpaceListenerPlugin() throws Exception {
    //TODO
  }

  /**
   * Test {@link SpaceService#unregisterSpaceListenerPlugin(org.exoplatform.social.core.space.SpaceListenerPlugin)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testUnregisterSpaceListenerPlugin() throws Exception {
    //TODO
  }

  /**
   * Test {@link SpaceService#initApp(Space)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testInitApp() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#initApps(Space)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testInitApps() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#deInitApps(Space)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testDeInitApps() throws Exception {
    //TODO Complete this
  }

  /**
   * Creates new space with out init apps.
   *
   * @param space
   * @param creator
   * @param invitedGroupId
   * @return
   * @since 1.2.0-GA
   */
  private Space createSpaceNonInitApps(Space space, String creator, String invitedGroupId) {
    // Creates new space by creating new group
    String groupId = null;
    try {
      groupId = SpaceUtils.createGroup(space.getDisplayName(), creator);
    } catch (SpaceException e) {
      LOG.error("Error while creating group", e);
    }

    if (invitedGroupId != null) {
      // Invites user in group join to new created space.
      // Gets users in group and then invites user to join into space.
      OrganizationService org = (OrganizationService) ExoContainerContext.getCurrentContainer().getComponentInstanceOfType(OrganizationService.class);
      try {
        PageList<User> groupMembersAccess = org.getUserHandler().findUsersByGroup(invitedGroupId);
        List<User> users = groupMembersAccess.getAll();

        for (User user : users) {
          String userId = user.getUserName();
          if (!userId.equals(creator)) {
            String[] invitedUsers = space.getInvitedUsers();
            if (!ArrayUtils.contains(invitedUsers, userId)) {
              invitedUsers = (String[]) ArrayUtils.add(invitedUsers, userId);
              space.setInvitedUsers(invitedUsers);
            }
          }
        }
      } catch (Exception e) {
        LOG.error("Failed to invite users from group " + invitedGroupId, e);
      }
    }
    String[] managers = new String[] { creator };
    space.setManagers(managers);
    space.setGroupId(groupId);
    space.setUrl(space.getPrettyName());
    try {
      spaceService.saveSpace(space, true);
    } catch (SpaceException e) {
      LOG.warn("Error while saving space", e);
    }
    return space;
  }

  /**
   * Test {@link SpaceService#addMember(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testAddMember() throws Exception {
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo"};
    String[] members = new String[] {};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);

    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    spaceService.addMember(savedSpace, "root");
    spaceService.addMember(savedSpace, "mary");
    spaceService.addMember(savedSpace, "john");
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getMembers().length must return 4", 4, savedSpace.getMembers().length);
    // Wait 3 secs to have activity stored
    try {
      IdentityManager identityManager = (IdentityManager) getContainer().getComponentInstanceOfType(IdentityManager.class);
      ActivityManager activityManager = (ActivityManager) getContainer().getComponentInstanceOfType(ActivityManager.class);
      Thread.sleep(3000);
      List<ExoSocialActivity> broadCastActivities = activityManager.getActivities(identityManager.getOrCreateIdentity(SpaceIdentityProvider.NAME, savedSpace.getPrettyName(), false), 0, 10);
      for (ExoSocialActivity activity : broadCastActivities) {
        activityManager.deleteActivity(activity);
      }
    } catch (InterruptedException e) {
      LOG.error(e.getMessage(), e);
    }
  }
  
  /**
   * Test {@link SpaceService#addMember(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testAddMemberSpecialCharacter() throws Exception {
    String reg = "^\\p{L}[\\p{L}\\d\\s._,-]+$";
    Pattern pattern = Pattern.compile(reg);
    Assert.assertTrue(pattern.matcher("user-new.1").matches());
    Assert.assertTrue(pattern.matcher("user.new").matches());
    Assert.assertTrue(pattern.matcher("user-new").matches());
  
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo"};
    String[] members = new String[] {};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);

    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    spaceService.addMember(savedSpace, "user-new.1");
    spaceService.addMember(savedSpace, "user.new");
    spaceService.addMember(savedSpace, "user-new");
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals(4, savedSpace.getMembers().length);
  }

  
  /**
   * Test {@link SpaceService#removeMember(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRemoveMember() throws Exception {
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo"};
    String[] members = new String[] {};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    spaceService.addMember(savedSpace, "root");
    spaceService.addMember(savedSpace, "mary");
    spaceService.addMember(savedSpace, "john");
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getMembers().length must return 4", 4, savedSpace.getMembers().length);

    spaceService.removeMember(savedSpace, "root");
    spaceService.removeMember(savedSpace, "mary");
    spaceService.removeMember(savedSpace, "john");
    Assert.assertEquals("savedSpace.getMembers().length must return 1", 1, savedSpace.getMembers().length);
    // Wait 3 secs to have activity stored
    try {
      IdentityManager identityManager = (IdentityManager) getContainer().getComponentInstanceOfType(IdentityManager.class);
      ActivityManager activityManager = (ActivityManager) getContainer().getComponentInstanceOfType(ActivityManager.class);
      Thread.sleep(3000);
      List<ExoSocialActivity> broadCastActivities = activityManager.getActivities(identityManager.getOrCreateIdentity(SpaceIdentityProvider.NAME, savedSpace.getPrettyName(), false), 0, 10);
      for (ExoSocialActivity activity : broadCastActivities) {
        activityManager.deleteActivity(activity);
      }
    } catch (InterruptedException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * Test {@link SpaceService#getMembers(Space)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetMembers() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    assertEquals("spaceService.getMembers(savedSpace).size() must return: " + savedSpace.getMembers().length, savedSpace.getMembers().length, spaceService.getMembers(savedSpace).size());
  }

  /**
   * Test {@link SpaceService#setLeader(Space, String, boolean)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testSetLeader() throws Exception {
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo", "tom"};
    String[] members = new String[] {"raul", "ghost", "dragon"};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);

    //Space space = this.getSpaceInstance(0);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    int managers = savedSpace.getManagers().length;
    spaceService.setLeader(savedSpace, "demo", true);
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getManagers().length must return: " + managers, managers, savedSpace.getManagers().length);

    spaceService.setLeader(savedSpace, "john", true);
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getManagers().length must return: " + managers + 1, managers + 1, savedSpace.getManagers().length);

    spaceService.setLeader(savedSpace, "demo", false);
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getManagers().length must return: " + managers, managers, savedSpace.getManagers().length);

    // Wait 3 secs to have activity stored
    try {
      IdentityManager identityManager = (IdentityManager) getContainer().getComponentInstanceOfType(IdentityManager.class);
      ActivityManager activityManager = (ActivityManager) getContainer().getComponentInstanceOfType(ActivityManager.class);
      Thread.sleep(3000);
      List<ExoSocialActivity> broadCastActivities = activityManager.getActivities(identityManager.getOrCreateIdentity(SpaceIdentityProvider.NAME, savedSpace.getPrettyName(), false), 0, 10);
      for (ExoSocialActivity activity : broadCastActivities) {
        activityManager.deleteActivity(activity);
      }
    } catch (InterruptedException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * Test {@link SpaceService#isLeader(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsLeader() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertTrue("spaceService.isLeader(savedSpace, \"demo\") must return true", spaceService.isLeader(savedSpace, "demo"));
    Assert.assertTrue("spaceService.isLeader(savedSpace, \"tom\") must return true", spaceService.isLeader(savedSpace, "tom"));
    Assert.assertFalse("spaceService.isLeader(savedSpace, \"mary\") must return false", spaceService.isLeader(savedSpace, "mary"));
    Assert.assertFalse("spaceService.isLeader(savedSpace, \"john\") must return false", spaceService.isLeader(savedSpace, "john"));
  }

  /**
   * Test {@link SpaceService#isOnlyLeader(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsOnlyLeader() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    Assert.assertFalse("spaceService.isOnlyLeader(savedSpace, \"tom\") must return false", spaceService.isOnlyLeader(savedSpace, "tom"));
    Assert.assertFalse("spaceService.isOnlyLeader(savedSpace, \"demo\") must return false", spaceService.isOnlyLeader(savedSpace, "demo"));

    savedSpace.setManagers(new String[] {"demo"});
    spaceService.updateSpace(savedSpace);
    Assert.assertTrue("spaceService.isOnlyLeader(savedSpace, \"demo\") must return true", spaceService.isOnlyLeader(savedSpace, "demo"));
    Assert.assertFalse("spaceService.isOnlyLeader(savedSpace, \"tom\") must return false", spaceService.isOnlyLeader(savedSpace, "tom"));

    savedSpace.setManagers(new String[] {"tom"});
    spaceService.updateSpace(savedSpace);
    Assert.assertFalse("spaceService.isOnlyLeader(savedSpace, \"demo\") must return false", spaceService.isOnlyLeader(savedSpace, "demo"));
    Assert.assertTrue("spaceService.isOnlyLeader(savedSpace, \"tom\") must return true", spaceService.isOnlyLeader(savedSpace, "tom"));
  }

  /**
   * Test {@link SpaceService#isMember(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsMember() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    Assert.assertTrue("spaceService.isMember(savedSpace, \"raul\") must return true", spaceService.isMember(savedSpace, "raul"));
    Assert.assertTrue("spaceService.isMember(savedSpace, \"ghost\") must return true", spaceService.isMember(savedSpace, "ghost"));
    Assert.assertTrue("spaceService.isMember(savedSpace, \"dragon\") must return true", spaceService.isMember(savedSpace, "dragon"));
    Assert.assertFalse("spaceService.isMember(savedSpace, \"stranger\") must return true", spaceService.isMember(savedSpace, "stranger"));
  }

  /**
   * Test {@link SpaceService#hasAccessPermission(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testHasAccessPermission() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    Assert.assertTrue("spaceService.hasAccessPermission(savedSpace, \"raul\") must return true", spaceService.hasAccessPermission(savedSpace, "raul"));
    Assert.assertTrue("spaceService.hasAccessPermission(savedSpace, \"ghost\") must return true", spaceService.hasAccessPermission(savedSpace, "ghost"));
    Assert.assertTrue("spaceService.hasAccessPermission(savedSpace, \"dragon\") must return true", spaceService.hasAccessPermission(savedSpace, "dragon"));
    Assert.assertTrue("spaceService.hasAccessPermission(savedSpace, \"tom\") must return true", spaceService.hasAccessPermission(savedSpace, "tom"));
    Assert.assertTrue("spaceService.hasAccessPermission(savedSpace, \"demo\") must return true", spaceService.hasAccessPermission(savedSpace, "demo"));
    Assert.assertTrue("spaceService.hasAccessPermission(savedSpace, \"root\") must return true", spaceService.hasAccessPermission(savedSpace, "root"));
    Assert.assertFalse("spaceService.hasAccessPermission(savedSpace, \"mary\") must return false", spaceService.hasAccessPermission(savedSpace, "mary"));
    Assert.assertFalse("spaceService.hasAccessPermission(savedSpace, \"john\") must return false", spaceService.hasAccessPermission(savedSpace, "john"));
  }

  /**
   * Test {@link SpaceService#hasEditPermission(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testHasEditPermission() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    Assert.assertTrue("spaceService.hasEditPermission(savedSpace, \"root\") must return true", spaceService.hasEditPermission(savedSpace, "root"));
    Assert.assertTrue("spaceService.hasEditPermission(savedSpace, \"demo\") must return true", spaceService.hasEditPermission(savedSpace, "demo"));
    Assert.assertTrue("spaceService.hasEditPermission(savedSpace, \"tom\") must return true", spaceService.hasEditPermission(savedSpace, "tom"));
    Assert.assertFalse("spaceService.hasEditPermission(savedSpace, \"mary\") must return false", spaceService.hasEditPermission(savedSpace, "mary"));
    Assert.assertFalse("spaceService.hasEditPermission(savedSpace, \"john\") must return false", spaceService.hasEditPermission(savedSpace, "john"));
    Assert.assertFalse("spaceService.hasEditPermission(savedSpace, \"raul\") must return false", spaceService.hasEditPermission(savedSpace, "raul"));
    Assert.assertFalse("spaceService.hasEditPermission(savedSpace, \"ghost\") must return false", spaceService.hasEditPermission(savedSpace, "ghost"));
    Assert.assertFalse("spaceService.hasEditPermission(savedSpace, \"dragon\") must return false", spaceService.hasEditPermission(savedSpace, "dragon"));
  }

  /**
   * Test {@link SpaceService#isInvited(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsInvited() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    Assert.assertTrue("spaceService.isInvited(savedSpace, \"register1\") must return true", spaceService.isInvited(savedSpace, "register1"));
    Assert.assertTrue("spaceService.isInvited(savedSpace, \"mary\") must return true", spaceService.isInvited(savedSpace, "mary"));
    Assert.assertFalse("spaceService.isInvited(savedSpace, \"demo\") must return false", spaceService.isInvited(savedSpace, "demo"));
    Assert.assertFalse("spaceService.isInvited(savedSpace, \"john\") must return false", spaceService.isInvited(savedSpace, "john"));
  }

  /**
   * Test {@link SpaceService#isPending(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testIsPending() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    Assert.assertTrue("spaceService.isPending(savedSpace, \"jame\") must return true", spaceService.isPending(savedSpace, "jame"));
    Assert.assertTrue("spaceService.isPending(savedSpace, \"paul\") must return true", spaceService.isPending(savedSpace, "paul"));
    Assert.assertTrue("spaceService.isPending(savedSpace, \"hacker\") must return true", spaceService.isPending(savedSpace, "hacker"));
    Assert.assertFalse("spaceService.isPending(savedSpace, \"mary\") must return false", spaceService.isPending(savedSpace, "mary"));
    Assert.assertFalse("spaceService.isPending(savedSpace, \"john\") must return false", spaceService.isPending(savedSpace, "john"));
  }

  /**
   * Test {@link SpaceService#installApplication(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testInstallApplication() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#activateApplication(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testActivateApplication() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#deactivateApplication(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testDeactivateApplication() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#removeApplication(Space, String, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRemoveApplication() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#requestJoin(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRequestJoin() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    int pendingUsersCount = space.getPendingUsers().length;
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be false",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
    spaceService.requestJoin(space, newPendingUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount + 1,
        pendingUsersCount + 1, space.getPendingUsers().length);
    Assert.assertTrue("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#revokeRequestJoin(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRevokeRequestJoin() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    int pendingUsersCount = space.getPendingUsers().length;
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser) must be false",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
    spaceService.requestJoin(space, newPendingUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount + 1,
        pendingUsersCount + 1, space.getPendingUsers().length);
    Assert.assertTrue("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));

    spaceService.revokeRequestJoin(space, newPendingUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount, pendingUsersCount, space.getPendingUsers().length);
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#inviteMember(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testInviteMember() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    int invitedUsersCount = savedSpace.getInvitedUsers().length;
    Assert.assertFalse("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return false",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
    spaceService.inviteMember(savedSpace, newInvitedUser.getRemoteId());
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getInvitedUsers().length must return: " + invitedUsersCount + 1,
        invitedUsersCount + 1, savedSpace.getInvitedUsers().length);
    Assert.assertTrue("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return true",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#revokeInvitation(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRevokeInvitation() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);
    int invitedUsersCount = savedSpace.getInvitedUsers().length;
    Assert.assertFalse("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return false",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
    spaceService.addInvitedUser(savedSpace, newInvitedUser.getRemoteId());
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getInvitedUsers().length must return: " + invitedUsersCount + 1,
        invitedUsersCount + 1, savedSpace.getInvitedUsers().length);
    Assert.assertTrue("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return true",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
    spaceService.revokeInvitation(savedSpace, newInvitedUser.getRemoteId());
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getInvitedUsers().length must return: " + invitedUsersCount,
        invitedUsersCount, savedSpace.getInvitedUsers().length);
    Assert.assertFalse("ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()) must return false",
        ArrayUtils.contains(savedSpace.getInvitedUsers(), newInvitedUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#acceptInvitation(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testAcceptInvitation() throws Exception {
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo"};
    String[] members = new String[] {};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);

    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    spaceService.acceptInvitation(savedSpace, "root");
    spaceService.acceptInvitation(savedSpace, "mary");
    spaceService.acceptInvitation(savedSpace, "john");
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getMembers().length must return 4", 4, savedSpace.getMembers().length);
    // Wait 3 secs to have activity stored
    try {
      IdentityManager identityManager = (IdentityManager) getContainer().getComponentInstanceOfType(IdentityManager.class);
      ActivityManager activityManager = (ActivityManager) getContainer().getComponentInstanceOfType(ActivityManager.class);
      Thread.sleep(3000);
      List<ExoSocialActivity> broadCastActivities = activityManager.getActivities(identityManager.getOrCreateIdentity(SpaceIdentityProvider.NAME, savedSpace.getPrettyName(), false), 0, 10);
      for (ExoSocialActivity activity : broadCastActivities) {
        activityManager.deleteActivity(activity);
      }
    } catch (InterruptedException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * Test {@link SpaceService#denyInvitation(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testDenyInvitation() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    spaceService.denyInvitation(savedSpace, "new member 1");
    spaceService.denyInvitation(savedSpace, "new member 2");
    spaceService.denyInvitation(savedSpace, "new member 3");
    Assert.assertEquals("savedSpace.getMembers().length must return 2", 2, savedSpace.getInvitedUsers().length);

    spaceService.denyInvitation(savedSpace, "raul");
    spaceService.denyInvitation(savedSpace, "ghost");
    spaceService.denyInvitation(savedSpace, "dragon");
    Assert.assertEquals("savedSpace.getMembers().length must return 2", 2, savedSpace.getInvitedUsers().length);

    spaceService.denyInvitation(savedSpace, "register1");
    spaceService.denyInvitation(savedSpace, "mary");
    Assert.assertEquals("savedSpace.getMembers().length must return 0", 0, savedSpace.getInvitedUsers().length);
  }

  /**
   * Test {@link SpaceService#validateRequest(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testValidateRequest() throws Exception {
    int number = 0;
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    space.setUrl(space.getPrettyName());
    String[] spaceManagers = new String[] {"demo"};
    String[] members = new String[] {};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(spaceManagers);
    space.setMembers(members);

    space = this.createSpaceNonInitApps(space, "demo", null);
    tearDownSpaceList.add(space);

    Space savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertNotNull("savedSpace must not be null", savedSpace);

    spaceService.validateRequest(savedSpace, "root");
    spaceService.validateRequest(savedSpace, "mary");
    spaceService.validateRequest(savedSpace, "john");
    savedSpace = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("savedSpace.getMembers().length must return 4", 4, savedSpace.getMembers().length);
    // Wait 3 secs to have activity stored
    try {
      IdentityManager identityManager = (IdentityManager) getContainer().getComponentInstanceOfType(IdentityManager.class);
      ActivityManager activityManager = (ActivityManager) getContainer().getComponentInstanceOfType(ActivityManager.class);
      Thread.sleep(3000);
      List<ExoSocialActivity> broadCastActivities = activityManager.getActivities(identityManager.getOrCreateIdentity(SpaceIdentityProvider.NAME, savedSpace.getPrettyName(), false), 0, 10);
      for (ExoSocialActivity activity : broadCastActivities) {
        activityManager.deleteActivity(activity);
      }
    } catch (InterruptedException e) {
      LOG.error(e.getMessage(), e);
    }
  }

  /**
   * Test {@link SpaceService#declineRequest(Space, String)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testDeclineRequest() throws Exception {
    Space space = this.getSpaceInstance(0);
    tearDownSpaceList.add(space);
    int pendingUsersCount = space.getPendingUsers().length;
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newPendingUser) must be false",
        ArrayUtils.contains(space.getPendingUsers(), newPendingUser.getRemoteId()));
    spaceService.addPendingUser(space, newInvitedUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount + 1,
        pendingUsersCount + 1, space.getPendingUsers().length);
    Assert.assertTrue("ArrayUtils.contains(space.getPendingUsers(), newInvitedUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newInvitedUser.getRemoteId()));

    spaceService.declineRequest(space, newInvitedUser.getRemoteId());
    space = spaceService.getSpaceByDisplayName(space.getDisplayName());
    Assert.assertEquals("space.getPendingUsers().length must return: " + pendingUsersCount,
        pendingUsersCount, space.getPendingUsers().length);
    Assert.assertFalse("ArrayUtils.contains(space.getPendingUsers(), newInvitedUser.getRemoteId()) must be true",
        ArrayUtils.contains(space.getPendingUsers(), newInvitedUser.getRemoteId()));
  }

  /**
   * Test {@link SpaceService#registerSpaceLifeCycleListener(SpaceLifeCycleListener)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testRegisterSpaceLifeCybleListener() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#unregisterSpaceLifeCycleListener(SpaceLifeCycleListener)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testUnRegisterSpaceLifeCycleListener() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#setPortletsPrefsRequired(org.exoplatform.social.core.application.PortletPreferenceRequiredPlugin)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testSetPortletsPrefsRequired() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#getPortletsPrefsRequired()}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetPortletsPrefsRequired() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService#setSpaceApplicationConfigPlugin(org.exoplatform.social.core.space.SpaceApplicationConfigPlugin)}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testSetSpaceApplicationConfigPlugin() throws Exception {
    //TODO Complete this
  }

  /**
   * Test {@link SpaceService}
   *
   * @throws Exception
   * @since 1.2.0-GA
   */
  public void testGetSpaceApplicationConfigPlugin() throws Exception {
    //TODO Complete this
  }
  
  /**
   * Test {@link org.exoplatform.social.core.storage.SpaceStorage#getVisibleSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.5-GA
   */
  public void testGetVisibleSpaces() throws Exception {
    int countSpace = 10;
    Space []listSpace = new Space[10];
    
    //there are 6 spaces with visible = 'private'
    for (int i = 0; i < countSpace; i ++) {
    
      if (i < 6)
         //[0->5] :: there are 6 spaces with visible = 'private'
        listSpace[i] = this.getSpaceInstance(i, Space.PRIVATE, Space.OPEN, "demo");
      else
        //[6->9]:: there are 4 spaces with visible = 'hidden'
        listSpace[i] = this.getSpaceInstance(i, Space.HIDDEN, Space.OPEN, "demo");
      
      spaceService.saveSpace(listSpace[i], true);
      tearDownSpaceList.add(listSpace[i]);
    }
    
    //visible with remoteId = 'demo'  return 10 spaces
    {
      List<Space> visibleAllSpaces = spaceService.getVisibleSpaces("demo", null);
      Assert.assertNotNull("visibleSpaces must not be  null", visibleAllSpaces);
      Assert.assertEquals("visibleSpaces() must return: " + countSpace, countSpace, visibleAllSpaces.size());
    }
    
    
  }
  
  
  
  /**
   * Test {@link org.exoplatform.social.core.storage.SpaceStorage#getVisibleSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.5-GA
   */
  public void testGetVisibleSpacesCloseRegistration() throws Exception {
    int countSpace = 10;
    Space []listSpace = new Space[10];
    
    //there are 6 spaces with visible = 'private'
    for (int i = 0; i < countSpace; i ++) {
    
      if (i < 6)
         //[0->5] :: there are 6 spaces with visible = 'private'
        listSpace[i] = this.getSpaceInstance(i, Space.PRIVATE, Space.CLOSE, "demo");
      else
        //[6->9]:: there are 4 spaces with visible = 'hidden'
        listSpace[i] = this.getSpaceInstance(i, Space.HIDDEN, Space.CLOSE, "demo");
      
      spaceService.saveSpace(listSpace[i], true);
      tearDownSpaceList.add(listSpace[i]);
    }
    
    //visible with remoteId = 'demo'  return 10 spaces
    {
      List<Space> visibleAllSpaces = spaceService.getVisibleSpaces("demo", null);
      Assert.assertNotNull("visibleSpaces must not be  null", visibleAllSpaces);
      Assert.assertEquals("visibleSpaces() must return: " + countSpace, countSpace, visibleAllSpaces.size());
    }
    
    //visible with remoteId = 'mary'  return 6 spaces: can see
    {
      int registrationCloseSpaceCount = 6;
      List<Space> registrationCloseSpaces = spaceService.getVisibleSpaces("mary", null);
      Assert.assertNotNull("registrationCloseSpaces must not be  null", registrationCloseSpaces);
      Assert.assertEquals("registrationCloseSpaces must return: " + registrationCloseSpaceCount, registrationCloseSpaceCount, registrationCloseSpaces.size());
    }
    
   
  }
  
  /**
   * Test {@link org.exoplatform.social.core.storage.SpaceStorage#getVisibleSpaces(String)}
   *
   * @throws Exception
   * @since 1.2.5-GA
   */
  public void testGetVisibleSpacesInvitedMember() throws Exception {
    int countSpace = 10;
    Space[] listSpace = new Space[10];
    
    //there are 6 spaces with visible = 'private'
    for (int i = 0; i < countSpace; i ++) {
    
      if (i < 6)
         //[0->5] :: there are 6 spaces with visible = 'private'
        listSpace[i] = this.getSpaceInstanceInvitedMember(i, Space.PRIVATE, Space.CLOSE, new String[] {"mary", "hacker"}, "demo");
      else
        //[6->9]:: there are 4 spaces with visible = 'hidden'
        listSpace[i] = this.getSpaceInstance(i, Space.HIDDEN, Space.CLOSE, "demo");
      
      spaceService.saveSpace(listSpace[i], true);
      tearDownSpaceList.add(listSpace[i]);
    }
    
    //visible with remoteId = 'demo'  return 10 spaces
    {
      List<Space> visibleAllSpaces = spaceService.getVisibleSpaces("demo", null);
      Assert.assertNotNull("visibleSpaces must not be  null", visibleAllSpaces);
      Assert.assertEquals("visibleSpaces() must return: " + countSpace, countSpace, visibleAllSpaces.size());
    }
    
    //visible with invited = 'mary'  return 6 spaces
    {
      int invitedSpaceCount1 = 6;
      List<Space> invitedSpaces1 = spaceService.getVisibleSpaces("mary", null);
      Assert.assertNotNull("invitedSpaces must not be  null", invitedSpaces1);
      Assert.assertEquals("invitedSpaces must return: " + invitedSpaceCount1, invitedSpaceCount1, invitedSpaces1.size());
    }
    
    //visible with invited = 'hacker'  return 6 spaces
    {
      int invitedSpaceCount1 = 6;
      List<Space> invitedSpaces1 = spaceService.getVisibleSpaces("hacker", null);
      Assert.assertNotNull("invitedSpaces must not be  null", invitedSpaces1);
      Assert.assertEquals("invitedSpaces must return: " + invitedSpaceCount1, invitedSpaceCount1, invitedSpaces1.size());
    }
    
    //visible with invited = 'paul'  return 6 spaces
    {
      int invitedSpaceCount2 = 6;
      List<Space> invitedSpaces2 = spaceService.getVisibleSpaces("paul", null);
      Assert.assertNotNull("invitedSpaces must not be  null", invitedSpaces2);
      Assert.assertEquals("invitedSpaces must return: " + invitedSpaceCount2, invitedSpaceCount2, invitedSpaces2.size());
    }
  }

  private Space populateData() throws Exception {
    String spaceDisplayName = "Space1";
    Space space1 = new Space();
    space1.setApp("Calendar;FileSharing");
    space1.setDisplayName(spaceDisplayName);
    space1.setPrettyName(space1.getDisplayName());
    String shortName = StringUtils.cleanString(spaceDisplayName);
    space1.setGroupId("/spaces/" + shortName);
    space1.setUrl(shortName);
    space1.setRegistration("validation");
    space1.setDescription("This is my first space for testing");
    space1.setType("classic");
    space1.setVisibility("public");
    space1.setPriority("2");
    String[] manager = new String []{"root"};
    String[] members = new String []{"demo", "john", "mary", "tom", "harry"};
    space1.setManagers(manager);
    space1.setMembers(members);

    spaceService.saveSpace(space1, true);
    return space1;
  }

  /**
   * Gets an instance of the space.
   *
   * @param number
   * @return
   * @throws Exception
   * @since 1.2.0-GA
   */
  private Space getSpaceInstance(int number) throws Exception {
    Space space = new Space();
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(Space.OPEN);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(Space.PUBLIC);
    space.setRegistration(Space.VALIDATION);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/space/space" + number);
    String[] managers = new String[] {"demo", "tom"};
    String[] members = new String[] {"raul", "ghost", "dragon"};
    String[] invitedUsers = new String[] {"register1", "mary"};
    String[] pendingUsers = new String[] {"jame", "paul", "hacker"};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(managers);
    space.setMembers(members);
    space.setUrl(space.getPrettyName());
    this.spaceService.saveSpace(space, true);
    return space;
  }
  
  /**
   * Gets an instance of Space.
   *
   * @param number
   * @return an instance of space
   */
  private Space getSpaceInstance(int number, String visible, String registration, String manager, String...members) {
    Space space = new Space();
    space.setApp("app");
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(registration);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(visible);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/spaces/space" + number);
    String[] managers = new String[] {manager};
    String[] invitedUsers = new String[] {};
    String[] pendingUsers = new String[] {};
    space.setInvitedUsers(invitedUsers);
    space.setPendingUsers(pendingUsers);
    space.setManagers(managers);
    space.setMembers(members);
    space.setUrl(space.getPrettyName());
    return space;
  }
  
  /**
   * Gets an instance of Space.
   *
   * @param number
   * @return an instance of space
   */
  private Space getSpaceInstanceInvitedMember(int number, String visible, String registration, String[] invitedMember, String manager, String...members) {
    Space space = new Space();
    space.setApp("app");
    space.setDisplayName("my space " + number);
    space.setPrettyName(space.getDisplayName());
    space.setRegistration(registration);
    space.setDescription("add new space " + number);
    space.setType(DefaultSpaceApplicationHandler.NAME);
    space.setVisibility(visible);
    space.setPriority(Space.INTERMEDIATE_PRIORITY);
    space.setGroupId("/spaces/space" + number);
    space.setUrl(space.getPrettyName());
    String[] managers = new String[] {manager};
    //String[] invitedUsers = new String[] {invitedMember};
    String[] pendingUsers = new String[] {};
    space.setInvitedUsers(invitedMember);
    space.setPendingUsers(pendingUsers);
    space.setManagers(managers);
    space.setMembers(members);
    return space;
  }

  private Space createMoreSpace(String spaceName) throws Exception {
    Space space2 = new Space();
    space2.setApp("Contact,Forum");
    space2.setDisplayName(spaceName);
    space2.setPrettyName(space2.getDisplayName());
    String shortName = StringUtils.cleanString(spaceName);
    space2.setGroupId("/spaces/" + shortName );
    space2.setUrl(shortName);
    space2.setRegistration("open");
    space2.setDescription("This is my second space for testing");
    space2.setType("classic");
    space2.setVisibility("public");
    space2.setPriority("2");

    spaceService.saveSpace(space2, true);

    return space2;
  }
}