// Copyright (c) 2011 Tim Niblett. All Rights Reserved.
//
// File:        TestBase64Codec.java  (16-Dec-2011)
// Author:      tim
//
// Copyright in the whole and every part of this source file belongs to
// Tim Niblett (the Author) and may not be used, sold, licenced, 
// transferred, copied or reproduced in whole or in part in 
// any manner or form or in or on any media to any person other than 
// in accordance with the terms of The Author's agreement
// or otherwise without the prior written consent of The Author.  All
// information contained in this source file is confidential information
// belonging to The Author and as such may not be disclosed other
// than in accordance with the terms of The Author's agreement, or
// otherwise, without the prior written consent of The Author.  As
// confidential information this source file must be kept fully and
// effectively secure at all times.
//


package com.cilogi.util;

import com.google.common.base.Charsets;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class TestBase64Codec  extends TestCase {
    static final Logger LOG = Logger.getLogger(TestBase64Codec.class);

    public TestBase64Codec() {
        PropertyConfigurator.configure(getClass().getResource("testlog.cfg"));
    }

    public void testJim() {
        String s = "VGltDQoNCk91ciBpbnZlc3RpZ2F0aW9ucyBpbmRpY2F0ZSB0aGF0IHRoZSBleGlzdGluZyB3ZWJz\n" +
                "aXRlIG9wZXJhdG9ycyBpbnZvbHZlZCBpbiB0aGUgc2FsZSBvZiBzcGlyaXRzIGJ1eSB0aGVpciBw\n" +
                "cm9kdWN0IGR1dHkgcGFpZCBmcm9tIGxvY2FsIHdob2xlc2FsZXJzIGFuZCBhcmUgaW4gYWxsIHBy\n" +
                "b2JhYmlsaXR5IHNtYWxsIHJldGFpbGVycyBvciBwb3NzaWJseSBzbWFsbCB3aG9sZXNhbGVycyB0\n" +
                "aGVtc2VsdmVzLiAgVGhpcyB3b3JrcyBmaW5lIGZvciB0aGVtIGZvciBzYWxlcyBpbiB0aGUgVUsu\n" +
                "Li50aGV5IHdpbGwgYWRkIG1hcmdpbiwgY2FycmlhZ2UgY2hhcmdlIGFuZCBWQVQgd2hlbiB0aGV5\n" +
                "IGNvbXBsZXRlIGEgc2FsZS4gVGhlaXIgcHJvZml0YWJpbGl0eSB3aWxsIGJlIGJhc2VkIG9uIGhv\n" +
                "dyBjb21wbGV0aXZlIHRoZWlyIHN1cHBsaWVyc+KAmSBwcmljZXMgYXJlIGFuZCB0aGUgbWFyZ2lu\n" +
                "IHRoZSB3ZWJzaXRlIG9wZXJhdG9yIHdpc2hlcyB0byBhY2hpZXZlLg0KDQpJbnZlc3RpZ2F0aW9u\n" +
                "cyBpbmRpY2F0ZSB0aGF0IGlmIHRoZXkgcmVjZWl2ZSBhbiBvcmRlciBmcm9tIGEgQ3VzdG9tZXIg\n" +
                "aW4gRnJhbmNlIGZvciBkZWxpdmVyeSBpbnRvIEZyYW5jZSB0aGV5IHdpbGwgc2VuZCBhIFVLIGR1\n" +
                "dHkgcGFpZCBwcm9kdWN0IHRvIEZyYW5jZSBhbmQgaWYgdGhleSBjb21wbHkgd2l0aCB0aGUgbGF3\n" +
                "IHRoZXkgd2lsbCBhbHNvIHBheSB0aGUgYXBwcm9wcmlhdGUgRnJlbmNoIGV4Y2lzZSBkdXR5LiBU\n" +
                "aGlzIHNjZW5hcmlvIGhhcyB0d28gcG9zc2libGUgb3V0Y29tZXM6DQoNCjEuICAgICAgIElmIGJv\n" +
                "dGggVUsgYW5kIEZyZW5jaCBleGNpc2UgZHV0aWVzIGFyZSBwYWlkICB0aGUgd2Vic2l0ZSB3aWxs\n" +
                "IHN0cnVnZ2xlIHRvIGJlIGNvbXBldGl0aXZlIGFuZCBpbiBhbGwgbGlrZWxpaG9vZCB0aGUgcHJp\n" +
                "Y2UgaW4gYSBsb2NhbCBGcmVuY2ggcmV0YWlsZXIgd2lsbCBiZSBmYXIgbW9yZSBhdHRyYWN0aXZl\n" +
                "IGluICBtb3N0IGluc3RhbmNlcy4gVGhpcyBsZWF2ZXMgdGhlbSBsaW1pdGVkIHRvIHByb2R1Y3Rz\n" +
                "IG5vdCByZWFkaWx5IGF2YWlsYWJsZSBpbiByZXRhaWwgb3V0bGV0cy4NCg0KMi4gICAgICAgVGhl\n" +
                "IHNlbGxlciBkb2VzIG5vdCBwYXkgdGhlIEZyZW5jaCBleGNpc2UgZHV0eSwgcmVtYWlucyBjb21w\n" +
                "ZXRpdGl2ZSBidXQgaXMgaW4gYnJlYWNoIG9mIHRoZSBsYXcgYW5kIHdpbGwgZmluZCBpdCBkaWZm\n" +
                "aWN1bHQgdG8gZ3JvdyBoaXMgYnVzaW5lc3MgYXMgc29vbmVyIG9yIGxhdGVyIGhpcyBDdXN0b21l\n" +
                "ciBiYXNlIHdpbGwgYmUgcmVjZWl2aW5nIGR1dHkgZGVtYW5kcyBmcm9tIEZyZW5jaCBDdXN0b21z\n" +
                "Lg0KDQpBdCBiZXN0IHRoZXkgd2lsbCBoYXZlIGFuIG9wcG9ydHVuaXN0aWMgYnVzaW5lc3MgbW9k\n" +
                "ZWwgd2hpY2ggYmVjYXVzZSBvZiBhIGN1bHR1cmUgb2Ygbm9uIGNvbXBsaWFuY2Ugd2lsbCBub3Qg\n" +
                "YmUgYWJsZSB0byBzdXN0YWluIGdyb3d0aCBvciBiZWNhdXNlIG9mIHRoZSBkb3VibGUgZHV0eSBo\n" +
                "aXQgd2lsbCBiZSBjb25zaXN0ZW50bHkgdW5jb21wZXRpdGl2ZS4gVGhleSB3aWxsIHByb2JhYmx5\n" +
                "IGFsc28gdmVyeSBvZnRlbiBub3QgYWRoZXJlIHRvIGVhY2ggaW5kaXZpZHVhbCBzdGF0ZS9tYXJr\n" +
                "ZXLigJlzIGhlYWx0aCB3YXJuaW5nIHJlcXVpcmVtZW50cywgdGF4IG9yIGR1dHkgc3RhbXAgcmVx\n" +
                "dWlyZW1lbnRzIGV0Yw0KDQpUaGlzIGlzIGp1c3Qgb25lIGFyZWEgd2hlcmUgQm9uZGVhdSB3aWxs\n" +
                "IGJlIGlubm92YXRpdmUuIEJ5IGRldmVsb3BpbmcgYSDigJx3aGl0ZSBoYXTigJ0gY3VsdHVyZSBh\n" +
                "bmQgaW5ub3ZhdGl2ZSBpbmZvcm1hdGlvbiBzeXN0ZW1zIGVuc3VyaW5nIGxlZ2FsIGFuZCBmaXNj\n" +
                "YWwgY29tcGxpYW5jZSBpbiBhbGwgbWFya2V0cyB3ZSBob3BlIHRvIGVuZGVhciBvdXJzZWx2ZXMg\n" +
                "dG8gdGhlIHByb2R1Y2VyL3N1cHBsaWVyIGJhc2UgYW5kIGNvbnNlcXVlbnRpYWxseSBiZSBjb25z\n" +
                "aXN0ZW50bHkgY29tcGxldGl2ZSBpbiBvdXIgcHVibGlzaGVkIHNlbGxpbmcgcHJpY2VzIGFuZCBo\n" +
                "YXZlIGEgYnVzaW5lc3MgbW9kZWwgYW5kIGNvbmNlcHQgd2l0aCBzZXJpb3VzIGdyb3d0aCBwb3Rl\n" +
                "bnRpYWwuDQoNClJlZ2FyZHMNCg0KSmltDQpGcm9tOiB0aW1AdGltbmlibGV0dC5uZXQgW21haWx0\n" +
                "bzp0aW1AdGltbmlibGV0dC5uZXRdIE9uIEJlaGFsZiBPZiBUaW0gTmlibGV0dA0KU2VudDogMTUg\n" +
                "RGVjZW1iZXIgMjAxMSAxMTo1NQ0KVG86IEpvaG5zdG9uZSwgUm9ubmllOyBDYXJiZXJyeSwgSmFt\n" +
                "ZXMNCkNjOiBQZXRlciBNb3dmb3J0aDsgTGlhbSBIdWdoZXMNClN1YmplY3Q6IE1lZXRpbmcgeWVz\n" +
                "dGVyZGF5DQoNCkppbSAmIFJvbm5pZSwNCg0KVGhhbmtzIHZlcnkgbXVjaCBmb3IgeW91ciB0aW1l\n" +
                "IHllc3RlcmRheS4gIEl0cyBhIHZlcnkgZXhjaXRpbmcgcHJvamVjdCB0byBiZSBpbnZvbHZlZCB3\n" +
                "aXRoLCBldmVuIGlmIHRhbmdlbnRpYWxseS4NCg0KSSdsbCBwcm9iYWJseSBiZSBzZW5kaW5nIHlv\n" +
                "dSBhbm5veWluZyBlbWFpbHMgb3ZlciB0aGUgbmV4dCB3ZWVrIG9yIHR3byBhcyBJIHN0YXJ0IHRv\n" +
                "IHB1bGwgdGhlIFNNQVJUIHByb3Bvc2FsIHRvZ2V0aGVyLA0KDQpKaW0sIGNvdWxkIEkgdHJvdWJs\n" +
                "ZSB5b3UgZm9yIGEgY291cGxlIG9mIHBhcmFncmFwaHMgb24gdGhlIGlzc3VlIG9mIHBheWluZyB0\n" +
                "YXggdHdpY2UsIHdoaWNoIHlvdSBtZW50aW9uZWQgeWVzdGVyZGF5Pw0KDQpJIG1ldCB3aXRoIExp\n" +
                "YW0gdGhpcyBBTSBhbmQgd2UgZGlzY3Vzc2VkIHRoZSBwb3NzaWJpbGl0eSBvZiBteSBtZWV0aW5n\n" +
                "IHVwIHdpdGggRGF2aWQgdG93YXJkcyB0aGUgZW5kIG9mIG5leHQgd2VlayAoVGh1cnNkYXkgb3Ig\n" +
                "RnJpZGF5KS4gIFdpbGwgdGhpcyBiZSBwb3NzaWJsZT8gIEknZCBsaWtlIHRvIGdldCBhIG1vcmUg\n" +
                "ZGV0YWlsZWQgdmlldyBvZiB3aGF0IGl0IGlzIGhlJ3MgZG9pbmcgZm9yIHRoZSBwcm9wb3NhbCB3\n" +
                "cml0ZS11cC4NCg0KVGhhbmtzIGFnYWluLg0KDQpUaW0NCg0KDQoNCkRpc2NsYWltZXINClRoaXMg\n" +
                "ZS1tYWlsIGFuZCBhbnkgZmlsZXMgdHJhbnNtaXR0ZWQgd2l0aCBpdCBhcmUgY29uZmlkZW50aWFs\n" +
                "IGFuZCBpbnRlbmRlZCBzb2xlbHkgZm9yIHRoZSB1c2Ugb2YgdGhlIGluZGl2aWR1YWwgb3IgbGVn\n" +
                "YWwgcGVyc29uIHRvIHdob20gdGhleSBhcmUgYWRkcmVzc2VkLiBUaGlzIGNvbW11bmljYXRpb24g\n" +
                "bWF5IGNvbnRhaW4gaW5mb3JtYXRpb24gdGhhdCBpcyBwcml2aWxlZ2VkLiBJZiB5b3UgYXJlIG5v\n" +
                "dCB0aGUgaW50ZW5kZWQgcmVjaXBpZW50IG9yIHRoZSBwZXJzb24gcmVzcG9uc2libGUgZm9yIGRl\n" +
                "bGl2ZXJpbmcgdGhlIGUtbWFpbCB0byB0aGUgaW50ZW5kZWQgcmVjaXBpZW50LCB5b3UgYXJlIGFk\n" +
                "dmlzZWQgdGhhdCB5b3UgaGF2ZSByZWNlaXZlZCB0aGlzIGUtbWFpbCBpbiBlcnJvciBhbmQgdGhh\n" +
                "dCBhbnkgdXNlLCBkaXNzZW1pbmF0aW9uLCBmb3J3YXJkaW5nLCBwcmludGluZywgY29weWluZyBv\n" +
                "ZiB0aGlzIGUtbWFpbCBpcyBzdHJpY3RseSBwcm9oaWJpdGVkLiBJZiB5b3UgaGF2ZSByZWNlaXZl\n" +
                "ZCB0aGlzIGUtbWFpbCBpbiBlcnJvciBwbGVhc2Ugbm90aWZ5IG91ciBJQ1QgRGVwdC4gKzQ0KDAp\n" +
                "MTQxLTgxMC04MjAzLiBPdXIgc3lzdGVtIHJ1bnMgQW50aS12aXJ1cyBzb2Z0d2FyZSBidXQgbm8g\n" +
                "Z3VhcmFudGVlIGlzIGdpdmVuIGFuZCB5b3UgbXVzdCBzYXRpc2Z5IHlvdXJzZWxmIHRoYXQgbm8g\n" +
                "Y29tcHV0ZXIgdmlydXNlcyBhcmUgYXR0YWNoZWQuDQoNCkFueSB2aWV3IGV4cHJlc3NlZCBpbiB0\n" +
                "aGlzIG1lc3NhZ2UgYXJlIHRob3NlIG9mIHRoZSBpbmRpdmlkdWFsIHNlbmRlciwgZXhjZXB0IHdo\n" +
                "ZXJlIHRoZSBzZW5kZXIgc3BlY2lmaWVzIGFuZCB3aXRoIGF1dGhvcml0eSwgc3RhdGVzIHRoZW0g\n" +
                "dG8gYmUgdGhlIHZpZXdzIG9mIEpvaG4gRyBSdXNzZWxsIChUcmFuc3BvcnQpIEx0ZC4NCg0KSm9o\n" +
                "biBHIFJ1c3NlbGwgKFRyYW5zcG9ydCkgTHRkLCByZWdpc3RlcmVkIGluIFNjb3RsYW5kIG51bWJl\n" +
                "ciAyMTE4OS4gUmVnaXN0ZXJlZCBPZmZpY2U6IERlYW5zaWRlIFJvYWQsIEhpbGxpbmd0b24sIEds\n" +
                "YXNnb3cgRzUyIDRYQi4gVGVsOiArNDQoMCkxNDEtODEwLTgyMDAgRmF4OiArNDQoMCkxNDEtODEw\n" +
                "LTgyMDcNCg==";
        String out = new String(Base64Codec.decode(s), Charsets.UTF_8);
        //LOG.debug("out = " + out);
    }

    public void testJim2() {
        String s = "PGh0bWw+DQo8aGVhZD4NCjxtZXRhIGh0dHAtZXF1aXY9IkNvbnRlbnQtVHlwZSIgY29udGVudD0i\n" +
                "dGV4dC9odG1sOyBjaGFyc2V0PXV0Zi04Ij4NCjxzdHlsZT4NCjwhLS0NCkBmb250LWZhY2UNCgl7\n" +
                "Zm9udC1mYW1pbHk6IkNhbWJyaWEgTWF0aCJ9DQpAZm9udC1mYWNlDQoJe2ZvbnQtZmFtaWx5OkNh\n" +
                "bGlicml9DQpAZm9udC1mYWNlDQoJe2ZvbnQtZmFtaWx5OlRhaG9tYX0NCnAuTXNvTm9ybWFsLCBs\n" +
                "aS5Nc29Ob3JtYWwsIGRpdi5Nc29Ob3JtYWwNCgl7bWFyZ2luOjBjbTsNCgltYXJnaW4tYm90dG9t\n" +
                "Oi4wMDAxcHQ7DQoJZm9udC1zaXplOjEyLjBwdDsNCglmb250LWZhbWlseToiVGltZXMgTmV3IFJv\n" +
                "bWFuIiwic2VyaWYifQ0KYTpsaW5rLCBzcGFuLk1zb0h5cGVybGluaw0KCXtjb2xvcjpibHVlOw0K\n" +
                "CXRleHQtZGVjb3JhdGlvbjp1bmRlcmxpbmV9DQphOnZpc2l0ZWQsIHNwYW4uTXNvSHlwZXJsaW5r\n" +
                "Rm9sbG93ZWQNCgl7Y29sb3I6cHVycGxlOw0KCXRleHQtZGVjb3JhdGlvbjp1bmRlcmxpbmV9DQpw\n" +
                "Lk1zb0xpc3RQYXJhZ3JhcGgsIGxpLk1zb0xpc3RQYXJhZ3JhcGgsIGRpdi5Nc29MaXN0UGFyYWdy\n" +
                "YXBoDQoJe21hcmdpbi10b3A6MGNtOw0KCW1hcmdpbi1yaWdodDowY207DQoJbWFyZ2luLWJvdHRv\n" +
                "bTowY207DQoJbWFyZ2luLWxlZnQ6MzYuMHB0Ow0KCW1hcmdpbi1ib3R0b206LjAwMDFwdDsNCglm\n" +
                "b250LXNpemU6MTIuMHB0Ow0KCWZvbnQtZmFtaWx5OiJUaW1lcyBOZXcgUm9tYW4iLCJzZXJpZiJ9\n" +
                "DQpzcGFuLkVtYWlsU3R5bGUxNw0KCXtmb250LWZhbWlseToiQ2FsaWJyaSIsInNhbnMtc2VyaWYi\n" +
                "Ow0KCWNvbG9yOiMxRjQ5N0R9DQouTXNvQ2hwRGVmYXVsdA0KCXt9DQpAcGFnZSBXb3JkU2VjdGlv\n" +
                "bjENCgl7bWFyZ2luOjcyLjBwdCA3Mi4wcHQgNzIuMHB0IDcyLjBwdH0NCmRpdi5Xb3JkU2VjdGlv\n" +
                "bjENCgl7fQ0Kb2wNCgl7bWFyZ2luLWJvdHRvbTowY219DQp1bA0KCXttYXJnaW4tYm90dG9tOjBj\n" +
                "bX0NCi0tPg0KPC9zdHlsZT4NCjwvaGVhZD4NCjxib2R5IGxhbmc9IkVOLUdCIiBsaW5rPSJibHVl\n" +
                "IiB2bGluaz0icHVycGxlIj4NCjxkaXYgY2xhc3M9IldvcmRTZWN0aW9uMSI+DQo8cCBjbGFzcz0i\n" +
                "TXNvTm9ybWFsIj48c3BhbiBzdHlsZT0iZm9udC1zaXplOjExLjBwdDsgZm9udC1mYW1pbHk6JnF1\n" +
                "b3Q7Q2FsaWJyaSZxdW90OywmcXVvdDtzYW5zLXNlcmlmJnF1b3Q7OyBjb2xvcjojMUY0OTdEIj5U\n" +
                "aW08L3NwYW4+PC9wPg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+PHNwYW4gc3R5bGU9ImZvbnQtc2l6\n" +
                "ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxWW90O0NhbGlicmkmcXVvdDssJnF1b3Q7c2Fucy1zZXJp\n" +
                "ZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+Jm5ic3A7PC9zcGFuPjwvcD4NCjxwIGNsYXNzPSJNc29O\n" +
                "b3JtYWwiPjxzcGFuIHN0eWxlPSJmb250LXNpemU6MTEuMHB0OyBmb250LWZhbWlseTomcXVvdDtD\n" +
                "YWxpYnJpJnF1b3Q7LCZxdW90O3NhbnMtc2VyaWYmcXVvdDs7IGNvbG9yOiMxRjQ5N0QiPk91ciBp\n" +
                "bnZlc3RpZ2F0aW9ucyBpbmRpY2F0ZSB0aGF0IHRoZSBleGlzdGluZyB3ZWJzaXRlIG9wZXJhdG9y\n" +
                "cyBpbnZvbHZlZCBpbiB0aGUgc2FsZSBvZiBzcGlyaXRzIGJ1eSB0aGVpciBwcm9kdWN0IGR1dHkg\n" +
                "cGFpZCBmcm9tIGxvY2FsIHdob2xlc2FsZXJzIGFuZCBhcmUNCiBpbiBhbGwgcHJvYmFiaWxpdHkg\t\n" +
                "c21hbGwgcmV0YWlsZXJzIG9yIHBvc3NpYmx5IHNtYWxsIHdob2xlc2FsZXJzIHRoZW1zZWx2ZXMu\n" +
                "ICZuYnNwO1RoaXMgd29ya3MgZmluZSBmb3IgdGhlbSBmb3Igc2FsZXMgaW4gdGhlIFVLLi4udGhl\n" +
                "eSB3aWxsIGFkZCBtYXJnaW4sIGNhcnJpYWdlIGNoYXJnZSBhbmQgVkFUIHdoZW4gdGhleSBjb21w\n" +
                "bGV0ZSBhIHNhbGUuIFRoZWlyIHByb2ZpdGFiaWxpdHkgd2lsbCBiZSBiYXNlZCBvbiBob3cgY29t\n" +
                "cGxldGl2ZSB0aGVpcg0KIHN1cHBsaWVyc+KAmSBwcmljZXMgYXJlIGFuZCB0aGUgbWFyZ2luIHRo\n" +
                "ZSB3ZWJzaXRlIG9wZXJhdG9yIHdpc2hlcyB0byBhY2hpZXZlLjwvc3Bhbj48L3A+DQo8cCBjbGFz\n" +
                "cz0iTXNvTm9ybWFsIj48c3BhbiBzdHlsZT0iZm9udC1zaXplOjExLjBwdDsgZm9udC1mYW1pbHk6\n" +
                "JnF1b3Q7Q2FsaWJyaSZxdW90OywmcXVvdDtzYW5zLXNlcmlmJnF1b3Q7OyBjb2xvcjojMUY0OTdE\n" +
                "Ij4mbmJzcDs8L3NwYW4+PC9wPg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+PHNwYW4gc3R5bGU9ImZv\n" +
                "bnQtc2l6ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90O0NhbGlicmkmcXVvdDssJnF1b3Q7c2Fu\n" +
                "cy1zZXJpZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+SW52ZXN0aWdhdGlvbnMgaW5kaWNhdGUgdGhh\n" +
                "dCBpZiB0aGV5IHJlY2VpdmUgYW4gb3JkZXIgZnJvbSBhIEN1c3RvbWVyIGluIEZyYW5jZSBmb3Ig\n" +
                "ZGVsaXZlcnkgaW50byBGcmFuY2UgdGhleSB3aWxsIHNlbmQgYSBVSyBkdXR5IHBhaWQgcHJvZHVj\n" +
                "dCB0byBGcmFuY2UgYW5kDQogaWYgdGhleSBjb21wbHkgd2l0aCB0aGUgbGF3IHRoZXkgd2lsbCBh\n" +
                "bHNvIHBheSB0aGUgYXBwcm9wcmlhdGUgRnJlbmNoIGV4Y2lzZSBkdXR5LiBUaGlzIHNjZW5hcmlv\n" +
                "IGhhcyB0d28gcG9zc2libGUgb3V0Y29tZXM6PC9zcGFuPjwvcD4NCjxwIGNsYXNzPSJNc29MaXN0\n" +
                "UGFyYWdyYXBoIiBzdHlsZT0idGV4dC1pbmRlbnQ6LTE4LjBwdCI+PHNwYW4gc3R5bGU9ImZvbnQt\n" +
                "c2l6ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90O0NhbGlicmkmcXVvdDssJnF1b3Q7c2Fucy1z\n" +
                "ZXJpZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+PHNwYW4gc3R5bGU9IiI+MS48c3BhbiBzdHlsZT0i\n" +
                "Zm9udDo3LjBwdCAmcXVvdDtUaW1lcyBOZXcgUm9tYW4mcXVvdDsiPiZuYnNwOyZuYnNwOyZuYnNw\n" +
                "OyZuYnNwOyZuYnNwOyZuYnNwOw0KPC9zcGFuPjwvc3Bhbj48L3NwYW4+PHNwYW4gc3R5bGU9ImZv\n" +
                "bnQtc2l6ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90O0NhbGlicmkmcXVvdDssJnF1b3Q7c2Fu\n" +
                "cy1zZXJpZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+SWYgYm90aCBVSyBhbmQgRnJlbmNoIGV4Y2lz\n" +
                "ZSBkdXRpZXMgYXJlIHBhaWQgJm5ic3A7dGhlIHdlYnNpdGUgd2lsbCBzdHJ1Z2dsZSB0byBiZSBj\n" +
                "b21wZXRpdGl2ZSBhbmQgaW4gYWxsIGxpa2VsaWhvb2QgdGhlIHByaWNlIGluIGEgbG9jYWwgRnJl\n" +
                "bmNoIHJldGFpbGVyIHdpbGwNCiBiZSBmYXIgbW9yZSBhdHRyYWN0aXZlIGluJm5ic3A7IG1vc3Qg\n" +
                "aW5zdGFuY2VzLiBUaGlzIGxlYXZlcyB0aGVtIGxpbWl0ZWQgdG8gcHJvZHVjdHMgbm90IHJlYWRp\n" +
                "bHkgYXZhaWxhYmxlIGluIHJldGFpbCBvdXRsZXRzLjwvc3Bhbj48L3A+DQo8cCBjbGFzcz0iTXNv\n" +
                "TGlzdFBhcmFncmFwaCIgc3R5bGU9InRleHQtaW5kZW50Oi0xOC4wcHQiPjxzcGFuIHN0eWxlPSJm\n" +
                "b250LXNpemU6MTEuMHB0OyBmb250LWZhbWlseTomcXVvdDtDYWxpYnJpJnF1b3Q7LCZxdW90O3Nh\n" +
                "bnMtc2VyaWYmcXVvdDs7IGNvbG9yOiMxRjQ5N0QiPjxzcGFuIHN0eWxlPSIiPjIuPHNwYW4gc3R5\n" +
                "bGU9ImZvbnQ6Ny4wcHQgJnF1b3Q7VGltZXMgTmV3IFJvbWFuJnF1b3Q7Ij4mbmJzcDsmbmJzcDsm\n" +
                "bmJzcDsmbmJzcDsmbmJzcDsmbmJzcDsNCjwvc3Bhbj48L3NwYW4+PC9zcGFuPjxzcGFuIHN0eWxl\n" +
                "PSJmb250LXNpemU6MTEuMHB0OyBmb250LWZhbWlseTomcXVvdDtDYWxpYnJpJnF1b3Q7LCZxdW90\n" +
                "O3NhbnMtc2VyaWYmcXVvdDs7IGNvbG9yOiMxRjQ5N0QiPlRoZSBzZWxsZXIgZG9lcyBub3QgcGF5\n" +
                "IHRoZSBGcmVuY2ggZXhjaXNlIGR1dHksIHJlbWFpbnMgY29tcGV0aXRpdmUgYnV0IGlzIGluIGJy\n" +
                "ZWFjaCBvZiB0aGUgbGF3IGFuZCB3aWxsIGZpbmQgaXQgZGlmZmljdWx0IHRvIGdyb3cgaGlzIGJ1\n" +
                "c2luZXNzIGFzIHNvb25lcg0KIG9yIGxhdGVyIGhpcyBDdXN0b21lciBiYXNlIHdpbGwgYmUgcmVj\n" +
                "ZWl2aW5nIGR1dHkgZGVtYW5kcyBmcm9tIEZyZW5jaCBDdXN0b21zLjwvc3Bhbj48L3A+DQo8cCBj\n" +
                "bGFzcz0iTXNvTm9ybWFsIj48c3BhbiBzdHlsZT0iZm9udC1zaXplOjExLjBwdDsgZm9udC1mYW1p\n" +
                "bHk6JnF1b3Q7Q2FsaWJyaSZxdW90OywmcXVvdDtzYW5zLXNlcmlmJnF1b3Q7OyBjb2xvcjojMUY0\n" +
                "OTdEIj4mbmJzcDs8L3NwYW4+PC9wPg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+PHNwYW4gc3R5bGU9\n" +
                "ImZvbnQtc2l6ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90O0NhbGlicmkmcXVvdDssJnF1b3Q7\n" +
                "c2Fucy1zZXJpZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+QXQgYmVzdCB0aGV5IHdpbGwgaGF2ZSBh\n" +
                "biBvcHBvcnR1bmlzdGljIGJ1c2luZXNzIG1vZGVsIHdoaWNoIGJlY2F1c2Ugb2YgYSBjdWx0dXJl\n" +
                "IG9mIG5vbiBjb21wbGlhbmNlIHdpbGwgbm90IGJlIGFibGUgdG8gc3VzdGFpbiBncm93dGggb3Ig\n" +
                "YmVjYXVzZSBvZiB0aGUgZG91YmxlDQogZHV0eSBoaXQgd2lsbCBiZSBjb25zaXN0ZW50bHkgdW5j\n" +
                "b21wZXRpdGl2ZS4gVGhleSB3aWxsIHByb2JhYmx5IGFsc28gdmVyeSBvZnRlbiBub3QgYWRoZXJl\n" +
                "IHRvIGVhY2ggaW5kaXZpZHVhbCBzdGF0ZS9tYXJrZXLigJlzIGhlYWx0aCB3YXJuaW5nIHJlcXVp\n" +
                "cmVtZW50cywgdGF4IG9yIGR1dHkgc3RhbXAgcmVxdWlyZW1lbnRzIGV0Yzwvc3Bhbj48L3A+DQo8\n" +
                "cCBjbGFzcz0iTXNvTm9ybWFsIj48c3BhbiBzdHlsZT0iZm9udC1zaXplOjExLjBwdDsgZm9udC1m\n" +
                "YW1pbHk6JnF1b3Q7Q2FsaWJyaSZxdW90OywmcXVvdDtzYW5zLXNlcmlmJnF1b3Q7OyBjb2xvcjoj\n" +
                "MUY0OTdEIj4mbmJzcDs8L3NwYW4+PC9wPg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+PHNwYW4gc3R5\n" +
                "bGU9ImZvbnQtc2l6ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90O0NhbGlicmkmcXVvdDssJnF1\n" +
                "b3Q7c2Fucy1zZXJpZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+VGhpcyBpcyBqdXN0IG9uZSBhcmVh\n" +
                "IHdoZXJlIEJvbmRlYXUgd2lsbCBiZSBpbm5vdmF0aXZlLiBCeSBkZXZlbG9waW5nIGEg4oCcd2hp\n" +
                "dGUgaGF04oCdIGN1bHR1cmUgYW5kIGlubm92YXRpdmUgaW5mb3JtYXRpb24gc3lzdGVtcyBlbnN1\n" +
                "cmluZyBsZWdhbCBhbmQgZmlzY2FsIGNvbXBsaWFuY2UNCiBpbiBhbGwgbWFya2V0cyB3ZSBob3Bl\n" +
                "IHRvIGVuZGVhciBvdXJzZWx2ZXMgdG8gdGhlIHByb2R1Y2VyL3N1cHBsaWVyIGJhc2UgYW5kIGNv\n" +
                "bnNlcXVlbnRpYWxseSBiZSBjb25zaXN0ZW50bHkgY29tcGxldGl2ZSBpbiBvdXIgcHVibGlzaGVk\n" +
                "IHNlbGxpbmcgcHJpY2VzIGFuZCBoYXZlIGEgYnVzaW5lc3MgbW9kZWwgYW5kIGNvbmNlcHQgd2l0\n" +
                "aCBzZXJpb3VzIGdyb3d0aCBwb3RlbnRpYWwuPC9zcGFuPjwvcD4NCjxwIGNsYXNzPSJNc29Ob3Jt\n" +
                "YWwiPjxzcGFuIHN0eWxlPSJmb250LXNpemU6MTEuMHB0OyBmb250LWZhbWlseTomcXVvdDtDYWxp\n" +
                "YnJpJnF1b3Q7LCZxdW90O3NhbnMtc2VyaWYmcXVvdDs7IGNvbG9yOiMxRjQ5N0QiPjwvc3Bhbj48\n" +
                "L3A+DQo8cCBjbGFzcz0iTXNvTm9ybWFsIj48c3BhbiBzdHlsZT0iZm9udC1zaXplOjExLjBwdDsg\n" +
                "Zm9udC1mYW1pbHk6JnF1b3Q7Q2FsaWJyaSZxdW90OywmcXVvdDtzYW5zLXNlcmlmJnF1b3Q7OyBj\n" +
                "b2xvcjojMUY0OTdEIj4mbmJzcDs8L3NwYW4+PC9wPg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+PHNw\n" +
                "YW4gc3R5bGU9ImZvbnQtc2l6ZToxMC4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90O0FyaWFsJnF1b3Q7\n" +
                "LCZxdW90O3NhbnMtc2VyaWYmcXVvdDs7IGNvbG9yOiMxRjQ5N0QiPlJlZ2FyZHM8L3NwYW4+PHNw\n" +
                "YW4gc3R5bGU9ImNvbG9yOiMxRjQ5N0QiPjwvc3Bhbj48L3A+DQo8cCBjbGFzcz0iTXNvTm9ybWFs\n" +
                "Ij48c3BhbiBzdHlsZT0iY29sb3I6IzFGNDk3RCI+Jm5ic3A7PC9zcGFuPjwvcD4NCjxwIGNsYXNz\n" +
                "PSJNc29Ob3JtYWwiPjxzcGFuIHN0eWxlPSJmb250LXNpemU6MTAuMHB0OyBmb250LWZhbWlseTom\n" +
                "cXVvdDtBcmlhbCZxdW90OywmcXVvdDtzYW5zLXNlcmlmJnF1b3Q7OyBjb2xvcjojMUY0OTdEIj5K\n" +
                "aW08L3NwYW4+PHNwYW4gc3R5bGU9ImZvbnQtc2l6ZToxMS4wcHQ7IGZvbnQtZmFtaWx5OiZxdW90\n" +
                "O0NhbGlicmkmcXVvdDssJnF1b3Q7c2Fucy1zZXJpZiZxdW90OzsgY29sb3I6IzFGNDk3RCI+PC9z\n" +
                "cGFuPjwvcD4NCjxkaXYgc3R5bGU9ImJvcmRlcjpub25lOyBib3JkZXItdG9wOnNvbGlkICNCNUM0\n" +
                "REYgMS4wcHQ7IHBhZGRpbmc6My4wcHQgMGNtIDBjbSAwY20iPg0KPHAgY2xhc3M9Ik1zb05vcm1h\n" +
                "bCI+PGI+PHNwYW4gbGFuZz0iRU4tVVMiIHN0eWxlPSJmb250LXNpemU6MTAuMHB0OyBmb250LWZh\n" +
                "bWlseTomcXVvdDtUYWhvbWEmcXVvdDssJnF1b3Q7c2Fucy1zZXJpZiZxdW90OyI+RnJvbTo8L3Nw\n" +
                "YW4+PC9iPjxzcGFuIGxhbmc9IkVOLVVTIiBzdHlsZT0iZm9udC1zaXplOjEwLjBwdDsgZm9udC1m\n" +
                "YW1pbHk6JnF1b3Q7VGFob21hJnF1b3Q7LCZxdW90O3NhbnMtc2VyaWYmcXVvdDsiPiB0aW1AdGlt\n" +
                "bmlibGV0dC5uZXQgW21haWx0bzp0aW1AdGltbmlibGV0dC5uZXRdDQo8Yj5PbiBCZWhhbGYgT2Yg\n" +
                "PC9iPlRpbSBOaWJsZXR0PGJyPg0KPGI+U2VudDo8L2I+IDE1IERlY2VtYmVyIDIwMTEgMTE6NTU8\n" +
                "YnI+DQo8Yj5Ubzo8L2I+IEpvaG5zdG9uZSwgUm9ubmllOyBDYXJiZXJyeSwgSmFtZXM8YnI+DQo8\n" +
                "Yj5DYzo8L2I+IFBldGVyIE1vd2ZvcnRoOyBMaWFtIEh1Z2hlczxicj4NCjxiPlN1YmplY3Q6PC9i\n" +
                "PiBNZWV0aW5nIHllc3RlcmRheTwvc3Bhbj48L3A+DQo8L2Rpdj4NCjxwIGNsYXNzPSJNc29Ob3Jt\n" +
                "YWwiPiZuYnNwOzwvcD4NCjxwIGNsYXNzPSJNc29Ob3JtYWwiPkppbSAmYW1wOyBSb25uaWUsPC9w\n" +
                "Pg0KPGRpdj4NCjxwIGNsYXNzPSJNc29Ob3JtYWwiPiZuYnNwOzwvcD4NCjwvZGl2Pg0KPGRpdj4N\n" +
                "CjxwIGNsYXNzPSJNc29Ob3JtYWwiPlRoYW5rcyB2ZXJ5IG11Y2ggZm9yIHlvdXIgdGltZSB5ZXN0\n" +
                "ZXJkYXkuICZuYnNwO0l0cyBhIHZlcnkgZXhjaXRpbmcgcHJvamVjdCB0byBiZSBpbnZvbHZlZCB3\n" +
                "aXRoLCBldmVuIGlmIHRhbmdlbnRpYWxseS48L3A+DQo8L2Rpdj4NCjxkaXY+DQo8cCBjbGFzcz0i\n" +
                "TXNvTm9ybWFsIj4mbmJzcDs8L3A+DQo8L2Rpdj4NCjxkaXY+DQo8cCBjbGFzcz0iTXNvTm9ybWFs\n" +
                "Ij5JJ2xsIHByb2JhYmx5IGJlIHNlbmRpbmcgeW91IGFubm95aW5nIGVtYWlscyBvdmVyIHRoZSBu\n" +
                "ZXh0IHdlZWsgb3IgdHdvIGFzIEkgc3RhcnQgdG8gcHVsbCB0aGUgU01BUlQgcHJvcG9zYWwgdG9n\n" +
                "ZXRoZXIsPC9wPg0KPC9kaXY+DQo8ZGl2Pg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+Jm5ic3A7PC9w\n" +
                "Pg0KPC9kaXY+DQo8ZGl2Pg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+SmltLCBjb3VsZCBJIHRyb3Vi\n" +
                "bGUgeW91IGZvciBhIGNvdXBsZSBvZiBwYXJhZ3JhcGhzIG9uIHRoZSBpc3N1ZSBvZiBwYXlpbmcg\n" +
                "dGF4IHR3aWNlLCB3aGljaCB5b3UgbWVudGlvbmVkIHllc3RlcmRheT88L3A+DQo8L2Rpdj4NCjxk\n" +
                "aXY+DQo8cCBjbGFzcz0iTXNvTm9ybWFsIj4mbmJzcDs8L3A+DQo8L2Rpdj4NCjxkaXY+DQo8cCBj\n" +
                "bGFzcz0iTXNvTm9ybWFsIj5JIG1ldCB3aXRoIExpYW0gdGhpcyBBTSBhbmQgd2UgZGlzY3Vzc2Vk\n" +
                "IHRoZSBwb3NzaWJpbGl0eSBvZiBteSBtZWV0aW5nIHVwIHdpdGggRGF2aWQgdG93YXJkcyB0aGUg\n" +
                "ZW5kIG9mIG5leHQgd2VlayAoVGh1cnNkYXkgb3IgRnJpZGF5KS4gJm5ic3A7V2lsbCB0aGlzIGJl\n" +
                "IHBvc3NpYmxlPyAmbmJzcDtJJ2QgbGlrZSB0byBnZXQgYSBtb3JlIGRldGFpbGVkIHZpZXcgb2Yg\n" +
                "d2hhdCBpdCBpcyBoZSdzIGRvaW5nIGZvciB0aGUgcHJvcG9zYWwNCiB3cml0ZS11cC48L3A+DQo8\n" +
                "L2Rpdj4NCjxkaXY+DQo8cCBjbGFzcz0iTXNvTm9ybWFsIj4mbmJzcDs8L3A+DQo8L2Rpdj4NCjxk\n" +
                "aXY+DQo8cCBjbGFzcz0iTXNvTm9ybWFsIj5UaGFua3MgYWdhaW4uPC9wPg0KPC9kaXY+DQo8ZGl2\n" +
                "Pg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+Jm5ic3A7PC9wPg0KPC9kaXY+DQo8ZGl2Pg0KPHAgY2xh\n" +
                "c3M9Ik1zb05vcm1hbCI+VGltPC9wPg0KPC9kaXY+DQo8ZGl2Pg0KPHAgY2xhc3M9Ik1zb05vcm1h\n" +
                "bCI+Jm5ic3A7PC9wPg0KPC9kaXY+DQo8ZGl2Pg0KPHAgY2xhc3M9Ik1zb05vcm1hbCI+Jm5ic3A7\n" +
                "PC9wPg0KPC9kaXY+DQo8L2Rpdj4NCjxmb250IHNpemU9IjEiPjxicj4NCjx1PkRpc2NsYWltZXI8\n" +
                "L3U+IDxicj4NClRoaXMgZS1tYWlsIGFuZCBhbnkgZmlsZXMgdHJhbnNtaXR0ZWQgd2l0aCBpdCBh\n" +
                "cmUgY29uZmlkZW50aWFsIGFuZCBpbnRlbmRlZCBzb2xlbHkgZm9yIHRoZSB1c2Ugb2YgdGhlIGlu\n" +
                "ZGl2aWR1YWwgb3IgbGVnYWwgcGVyc29uIHRvIHdob20gdGhleSBhcmUgYWRkcmVzc2VkLiBUaGlz\n" +
                "IGNvbW11bmljYXRpb24gbWF5IGNvbnRhaW4gaW5mb3JtYXRpb24gdGhhdCBpcyBwcml2aWxlZ2Vk\n" +
                "LiBJZiB5b3UgYXJlIG5vdCB0aGUgaW50ZW5kZWQgcmVjaXBpZW50DQogb3IgdGhlIHBlcnNvbiBy\n" +
                "ZXNwb25zaWJsZSBmb3IgZGVsaXZlcmluZyB0aGUgZS1tYWlsIHRvIHRoZSBpbnRlbmRlZCByZWNp\n" +
                "cGllbnQsIHlvdSBhcmUgYWR2aXNlZCB0aGF0IHlvdSBoYXZlIHJlY2VpdmVkIHRoaXMgZS1tYWls\n" +
                "IGluIGVycm9yIGFuZCB0aGF0IGFueSB1c2UsIGRpc3NlbWluYXRpb24sIGZvcndhcmRpbmcsIHBy\n" +
                "aW50aW5nLCBjb3B5aW5nIG9mIHRoaXMgZS1tYWlsIGlzIHN0cmljdGx5IHByb2hpYml0ZWQuIElm\n" +
                "IHlvdSBoYXZlIHJlY2VpdmVkDQogdGhpcyBlLW1haWwgaW4gZXJyb3IgcGxlYXNlIG5vdGlmeSBv\n" +
                "dXIgSUNUIERlcHQuICYjNDM7NDQoMCkxNDEtODEwLTgyMDMuIE91ciBzeXN0ZW0gcnVucyBBbnRp\n" +
                "LXZpcnVzIHNvZnR3YXJlIGJ1dCBubyBndWFyYW50ZWUgaXMgZ2l2ZW4gYW5kIHlvdSBtdXN0IHNh\n" +
                "dGlzZnkgeW91cnNlbGYgdGhhdCBubyBjb21wdXRlciB2aXJ1c2VzIGFyZSBhdHRhY2hlZC4NCjxi\n" +
                "cj4NCjxicj4NCkFueSB2aWV3IGV4cHJlc3NlZCBpbiB0aGlzIG1lc3NhZ2UgYXJlIHRob3NlIG9m\n" +
                "IHRoZSBpbmRpdmlkdWFsIHNlbmRlciwgZXhjZXB0IHdoZXJlIHRoZSBzZW5kZXIgc3BlY2lmaWVz\n" +
                "IGFuZCB3aXRoIGF1dGhvcml0eSwgc3RhdGVzIHRoZW0gdG8gYmUgdGhlIHZpZXdzIG9mIEpvaG4g\n" +
                "RyBSdXNzZWxsIChUcmFuc3BvcnQpIEx0ZC4NCjxicj4NCjxicj4NCkpvaG4gRyBSdXNzZWxsIChU\n" +
                "cmFuc3BvcnQpIEx0ZCwgcmVnaXN0ZXJlZCBpbiBTY290bGFuZCBudW1iZXIgMjExODkuIFJlZ2lz\n" +
                "dGVyZWQgT2ZmaWNlOiBEZWFuc2lkZSBSb2FkLCBIaWxsaW5ndG9uLCBHbGFzZ293IEc1MiA0WEIu\n" +
                "IFRlbDogJiM0Mzs0NCgwKTE0MS04MTAtODIwMCBGYXg6ICYjNDM7NDQoMCkxNDEtODEwLTgyMDcN\n" +
                "CjwvZm9udD4NCjwvYm9keT4NCjwvaHRtbD4NCg==";
        char c = s.charAt(1726);
        String out = new String(Base64Codec.decode(s), Charsets.UTF_8);
        //LOG.debug("out = " + out);
    }
}
