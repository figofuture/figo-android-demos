package com.figo.cloth;

public class Vec3 {
	public float f[] = new float[3];
	Vec3(float x, float y, float z)
	{
		f[0] =x;
		f[1] =y;
		f[2] =z;
	}
	
	float length()
	{
		return (float) Math.sqrt(f[0]*f[0]+f[1]*f[1]+f[2]*f[2]);
	}
	
	Vec3 normalized()
	{
		float l = length();
		return new Vec3(f[0]/l,f[1]/l,f[2]/l);
	}
	Vec3 cross(final Vec3 v)
	{
		return new Vec3(f[1]*v.f[2] - f[2]*v.f[1], f[2]*v.f[0] - f[0]*v.f[2], f[0]*v.f[1] - f[1]*v.f[0]);
	}

	float dot(final Vec3 v)
	{
		return f[0]*v.f[0] + f[1]*v.f[1] + f[2]*v.f[2];
	}
	
}
