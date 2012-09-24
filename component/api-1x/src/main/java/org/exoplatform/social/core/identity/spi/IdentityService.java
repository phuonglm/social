package org.exoplatform.social.core.identity.spi;


import org.exoplatform.commons.utils.ListAccess;
import org.exoplatform.social.core.identity.IdentityProvider;
import org.exoplatform.social.core.identity.IdentityProviderPlugin;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.identity.model.Profile;
import org.exoplatform.social.core.profile.ProfileFilter;
import org.exoplatform.social.core.profile.ProfileListenerPlugin;

/**
 * Created with IntelliJ IDEA.
 * User: exo1
 * Date: 9/21/12
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IdentityService {
    /**
     * Gets an Identity object provided by a identity provider.
     *
     * @param identityId  the uuid of identity.
     * @param loadProfile force load or reload profile
     * @return the Identity object provided from identity provider.
     */
    Identity get(String identityId, boolean loadProfile);

    /**
     * Gets or creates an Identity object provided by a identity provider.
     *
     * @param providerId               the name of identity provider name id.
     * @param remoteId                 the identifier that identifies the identity in the specific identity provider.
     * @return the Identity object provided from identity provider.
     */
    Identity create(String providerId, String remoteId);

    /**
     * Gets or creates an Identity object provided by a identity provider.
     *
     * @param identityId               the uuid of identity.
     * @return the Identity object provided from identity provider.
     */
    Identity delete(String identityId);

    /**
     * Hard deletes an existing identity.
     *
     * @param identityId the existing identityId.
     * @param hardDelete force remove identity from system.
     */
    Identity delete(String identityId, boolean hardDelete);

    /**
     * Updates existing identity's properties.
     *
     * @param identity the identity to be updated.
     * @return the updated identity.
     * @since  1.2.0-GA
     */
    Identity update(Identity identity);

    /**
     * Gets or creates an Identity object provided by a identity provider.
     *
     * @param profileFilter  the filter
     * @return the Identity object provided from identity provider.
     */
    ListAccess<Identity> findByProfile(ProfileFilter profileFilter);


    /**
     * Gets a profile associated with a provided identity.
     *
     * @param identity the provided identity
     * @return the profile associated with the provided identity
     * @since  1.2.0-GA
     */
    Profile getProfile(Identity identity);

    /**
     * Updates an existing profile.
     *
     * @param existingProfile the existing profile
     * @since  1.2.0-GA
     */
    void updateProfile(Profile existingProfile);

    /**
     * Adds an identity provider to identity manager.
     *
     * @param identityProvider an identity provider
     */
    void addIdentityProvider(IdentityProvider<?> identityProvider);

    /**
     * Registers one or more {@link IdentityProvider} through an
     * {@link org.exoplatform.social.core.identity.IdentityProviderPlugin}.
     *
     * @param plugin
     */
    void registerIdentityProviders(IdentityProviderPlugin plugin);

    /**
     * Remove an existing identity provider.
     *
     * @param identityProvider the existing identity provider
     * @since 1.2.0-GA
     */
    void removeIdentityProvider(IdentityProvider<?> identityProvider);

    /**
     * Registers a profile listener plugin by external compnent plugin mechanism.
     *
     * For example:
     * <pre>
     *  &lt;external-component-plugins&gt;
     *    &lt;target-component&gt;org.exoplatform.social.core.manager.IdentityManager&lt;/target-component&gt;
     *    &lt;component-plugin&gt;
     *      &lt;name&gt;ProfileUpdatesPublisher&lt;/name&gt;
     *      &lt;set-method&gt;registerProfileListener&lt;/set-method&gt;
     *      &lt;type&gt;org.exoplatform.social.core.application.ProfileUpdatesPublisher&lt;/type&gt;
     *    &lt;/component-plugin&gt;
     *  &lt;/external-component-plugins&gt;
     * </pre>
     *
     * @param profileListenerPlugin a profile listener plugin
     * @since 1.2.0-GA
     */
    void registerProfileListener(ProfileListenerPlugin profileListenerPlugin);



}
