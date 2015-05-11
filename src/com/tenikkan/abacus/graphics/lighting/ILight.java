package com.tenikkan.abacus.graphics.lighting;

import com.tenikkan.abacus.math.Vector4f;

public interface ILight
{
    public Vector4f getDirection(Vector4f destination);
}
