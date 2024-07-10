/*
 * Copyright (c) 2021 eBay Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.ebay.commerce.notification.constants;


import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Constants {

    public static final List<String> APPLICABLE_SCOPES = Arrays.asList(new String[]{"https://api.ebay.com/oauth/api_scope"
            ,"https://api.ebay.com/oauth/api_scope/buy.item.stream"
    });
    public static final String ALGO = "%swith%s";
    public static Pattern KEY_PATTERN = Pattern.compile("-----BEGIN PUBLIC KEY-----(.*?)-----END PUBLIC KEY-----", Pattern.DOTALL);


}
