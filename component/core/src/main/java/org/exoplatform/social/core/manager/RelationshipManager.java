/*
 * Copyright (C) 2003-2010 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.social.core.manager;

import java.util.List;

import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.profile.ProfileFilter;
import org.exoplatform.social.core.relationship.model.Relationship;
import org.exoplatform.social.core.storage.RelationshipStorageException;

/**
 * The RelationshipManager is used to work with connections between identities:
 *
 * <ul>
 *   <li>Get relationship between 2 identities.</li>
 *   <li>Interact between identities: invite, confirm, deny, ignore.</li>
 *   <li>Get list access to get list of connections, incoming, outgoing.</li>
 * </ul>
 *
 * @author <a href="http://hoatle.net">hoatle (hoatlevan at gmail dot com)</a>
 */
public interface RelationshipManager {
  /**
   * Gets a relationship by its id.
   *
   * @param relationshipId the relationship id
   * @return an existing relationship or null
   * @since  1.2.0-GA
   */
  Relationship get(String relationshipId);

  /**
   * Gets a relationship between 2 identities.
   *
   * @param identity1 one identity
   * @param identity2 the other identity
   * @return the relationship between 2 identities
   * @since  1.2.0-GA
   */
  Relationship get(Identity identity1, Identity identity2);

  /**
   * Updates an existing relationship.
   *
   * @param existingRelationship the existing relationship
   * @since 1.2.0-GA
   */
  void update(Relationship existingRelationship);

  /**
   * Deletes an existing relationship.
   *
   * @param existingRelationship the existing relationship
   * @since 1.2.0-GA
   */
  void delete(Relationship existingRelationship);

  /**
   * Invites one identity to connected with an other identity. The first argument must be the sender identity. The
   * second argument must be the identity who is invited to connect.
   * <p/>
   * One identity is not allowed to invite himself to connect.
   *
   * @param invitingIdentity the sender identity
   * @param invitedIdentity  the identity who is invited
   * @since 1.3.0-GA
   */
  //void invite(Identity invitingIdentity, Identity invitedIdentity);

  /**
   * Invites one identity to connected with an other identity. The first argument must be the sender identity. The
   * second argument must be the identity who is invited to connect.
   * <p/>
   * One identity is not allowed to invite himself to connect.
   *
   * @param invitingIdentity the sender identity
   * @param invitedIdentity  the identity who is invited
   * @since 1.2.0-GA
   */
  Relationship inviteToConnect(Identity invitingIdentity, Identity invitedIdentity);

  /**
   * The invited identity confirms to connect to an inviting identity. The invited identity is not allowed to confirm if
   * he's not invited to connect by inviting identity.
   *
   * @param invitedIdentity  the invited identity
   * @param invitingIdentity the inviting identity
   * @since 1.2.0-GA
   */
  void confirm(Identity invitedIdentity, Identity invitingIdentity);

  /**
   * The invited identity denies to connect to an inviting identity.
   * The invited identity is not allowed to deny if he's not invited to connect by inviting identity.
   *
   * @param invitedIdentity  the invited identity
   * @param invitingIdentity the inviting identity
   * @since 1.2.0-GA
   */
  void deny(Identity invitedIdentity, Identity invitingIdentity);

  /**
   * The invited identity ignores to connect to an inviting identity.
   * <p/>
   * If the invited identity ignores the inviting identity, the inviting identity can not invite this invited identity
   * any more.
   *
   * @param invitedIdentity  the invited identity
   * @param invitingIdentity the inviting identity
   * @since 1.2.0-GA
   */
  void ignore(Identity invitedIdentity, Identity invitingIdentity);

  /**
   * Gets the list access to get a list of identity who is connected with the provided identity.
   *
   * @param existingIdentity the existing provided identity
   * @return the list access
   * @since 1.2.0-GA
   */
  ListAccess<Identity> getConnections(Identity existingIdentity);

  /**
   * Gets the list access to get a list of identity who invited to connect to the provided identity.
   *
   * @param existingIdentity the existing provided identity
   * @return the list access
   * @since  1.3.0-GA
   */
  //ListAccess<Identity> getIncoming(Identity existingIdentity);

  /**
   * Gets the list access to get a list of identity who invited to connect to the provided identity.
   *
   * @param existingIdentity the existing provided identity
   * @return the list access
   * @since  1.2.0-GA
   */
  ListAccess<Identity> getIncomingWithListAccess(Identity existingIdentity);

  /**
   * Gets the list access to get a list of identity who was invited to connect by the provided identity.
   *
   * @param existingIdentity the existing provided identity
   * @return the list access
   * @since  1.2.0-GA
   */
  ListAccess<Identity> getOutgoing(Identity existingIdentity);

  /**
   * Gets the list access to get identities who is connected, was invited or invited the provided identity.
   *
   * @param existingIdentity the existing provided identity
   * @return the list access
   * @since  1.3.0-GA
   */
  //ListAccess<Identity> getAll(Identity existingIdentity);


  /**
   * Gets the list access to get identities who is connected, was invited or invited the provided identity.
   *
   * @param existingIdentity the existing provided identity
   * @return the list access
   * @since  1.2.0-GA
   */
  ListAccess<Identity> getAllWithListAccess(Identity existingIdentity);

  /**
   * Gets the relationship status between 2 identities.
   *
   * @param identity1 the identity1
   * @param identity2 the identity2
   * @return the relationship type
   * @since 1.2.0-GA
   */
  Relationship.Type getStatus(Identity identity1, Identity identity2);


  /**
   * Gets pending relationships of sender that match with identities.
   *
   * @param sender the sender
   * @param identities the identities
   * @return the pending relationships
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getIncomingWithListAccess(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getPending(Identity sender, List<Identity> identities) throws RelationshipStorageException;

  /**
   * Gets list of required validation relationship of receiver
   *
   * @param receiver the receiver identity
   * @return the list of relationships
   * @throws RelationshipStorageException
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getIncomingWithListAccess(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getIncoming(Identity receiver) throws RelationshipStorageException;

  /**
   * Gets list of required validation relationship of receiver that match with
   * identities.
   *
   * @param receiver the receiver
   * @param identities the identities
   * @return the pending require validation relationships
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getIncomingWithListAccess(Identity)} instead.
   *             Will be removed by 1.3.x.
   */
  @Deprecated
  List<Relationship> getIncoming(Identity receiver, List<Identity> identities) throws RelationshipStorageException;

  /**
   * Gets list of confirmed relationship of identity.
   *
   * @param identity the identity
   * @return the confirmed relationships
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getConnections(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getConfirmed(Identity identity) throws RelationshipStorageException;

  /**
   * Gets list of confirmed relationship of identity that match with identities.
   *
   * @param identity the identity
   * @param identities the identities
   * @return the list of confirmed relationship
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getConnections(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getConfirmed(Identity identity, List<Identity> identities) throws RelationshipStorageException;

  /**
   * Returns all the relationship of a given identity with other identity.
   *
   * @param identity the identity
   * @return the list relationships
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getAllWithListAccess(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getAll(Identity identity) throws RelationshipStorageException;

  /**
   * Returns all the relationship of a given identity with other identity in identities.
   *
   * @param identity   the identity
   * @param identities the list identity the identities
   * @return the list relationships
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getAllWithListAccess(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getAll(Identity identity, List<Identity> identities) throws RelationshipStorageException;

  /**
   * Returns all the relationship of a given identity with other identity in
   * identities.
   *
   * @param identity the identity
   * @param type the status
   * @param identities the list identity the identities
   * @return the list
   * @throws RelationshipStorageException the exception
   * @since 1.2.0-Beta1
   * @deprecated Use {@link #getAllWithListAccess(Identity)} instead.
   *             Will be removed by 1.3.x
   */
  @Deprecated
  List<Relationship> getAll(Identity identity, Relationship.Type type,
                            List<Identity> identities) throws RelationshipStorageException;
  
  
  /**
   * Gets the list access to get a list of identities who is connected with the provided identity
   * and filtered by profile filter.
   * 
   * @param existingIdentity the provided identity.
   * @param profileFilter the provided profile filter.
   * @return the list access
   * @since 1.2.3
   * 
   */
  ListAccess<Identity> getConnectionsByFilter(Identity existingIdentity, ProfileFilter profileFilter);
  
  /**
   * Gets the list access to get a list of identities who invited to connect to the provided identity
   * and filtered by profile filter.
   *
   * @param existingIdentity the provided identity
   * @param profileFilter    the provided profile filter
   * @return the list access
   * @since  1.2.3
   */
  ListAccess<Identity> getIncomingByFilter(Identity existingIdentity, ProfileFilter profileFilter);
  
  /**
   * Gets the list access to get a list of identities who was invited by the provided identity to connect
   * and filtered by profile filter.
   *
   * @param existingIdentity the provided identity
   * @param profileFilter    the provided profile filter
   * @return the list access
   * @since  1.2.3
   */
  ListAccess<Identity> getOutgoingByFilter(Identity existingIdentity, ProfileFilter profileFilter);
  
}