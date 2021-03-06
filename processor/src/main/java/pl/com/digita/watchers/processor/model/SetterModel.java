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

package pl.com.digita.watchers.processor.model;

import java.util.Objects;

public class SetterModel
{
    private String setterName;
    private String parameterType;

    public SetterModel(String setterName, String parameterType)
    {
        this.setterName = setterName;
        this.parameterType = parameterType;
    }

    public String getSetterName()
    {
        return setterName;
    }

    public void setSetterName(String setterName)
    {
        this.setterName = setterName;
    }

    public String getParameterType()
    {
        return parameterType;
    }

    public void setParameterType(String parameterType)
    {
        this.parameterType = parameterType;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof SetterModel)) return false;
        SetterModel that = (SetterModel) o;
        return Objects.equals(setterName, that.setterName) &&
                Objects.equals(parameterType, that.parameterType);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(setterName, parameterType);
    }


    @Override
    public String toString()
    {
        return setterName + " (" + parameterType + ")";
    }
}
