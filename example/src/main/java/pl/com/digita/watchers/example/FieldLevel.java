/*
 * Copyright (c) 2017. Piotr Potulski
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.com.digita.watchers.example;

import pl.com.digita.watchers.library.annotations.Observe;

public class FieldLevel
{
    @Observe
    private String fieldLevelField1;

    @Observe
    private int fieldLevelField2;

    public String getFieldLevelField1()
    {
        return fieldLevelField1;
    }

    public void setFieldLevelField1(String fieldLevelField1)
    {
        this.fieldLevelField1 = fieldLevelField1;
    }

    public int getFieldLevelField2()
    {
        return fieldLevelField2;
    }

    public void setFieldLevelField2(int fieldLevelField2)
    {
        this.fieldLevelField2 = fieldLevelField2;
    }
}
