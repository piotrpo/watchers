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

@Observe
public class ClassLevel
{
    private String classLevelField1;
    private int classLevelField2;

    public String getClassLevelField1()
    {
        return classLevelField1;
    }

    public void setClassLevelField1(String classLevelField1)
    {
        this.classLevelField1 = classLevelField1;
    }

    public int getClassLevelField2()
    {
        return classLevelField2;
    }

    public void setClassLevelField2(int classLevelField2)
    {
        this.classLevelField2 = classLevelField2;
    }
}
