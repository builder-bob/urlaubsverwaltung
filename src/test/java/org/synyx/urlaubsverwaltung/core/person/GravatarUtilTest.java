package org.synyx.urlaubsverwaltung.core.person;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * @author Aljona Murygina
 */
public class GravatarUtilTest {

    @Test
    public void testCreateImgURL() {

        String email = "FraU.LyOner@NeT.de";
        String url = GravatarUtil.createImgURL(email);

        assertThat(url, is("https://gravatar.com/avatar/3d12daeb3e1bc6dce51ff1f1a0357df6"));
    }
}
