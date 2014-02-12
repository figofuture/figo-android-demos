package com.figo.cloth;

public class Constraint {

	public static final int CONSTRAINT_ITERATIONS = 15;
	float rest_distance;
	Particle p1, p2;
	
	Constraint(Particle p1, Particle p2)
	{
		Vec3 vec = new Vec3(0,0,0);
		vec.f[0] = p1.getPos().f[0] - p2.getPos().f[0];
		vec.f[1] = p1.getPos().f[1] - p2.getPos().f[1];
		vec.f[2] = p1.getPos().f[2] - p2.getPos().f[2];
		rest_distance = vec.length();
	}
	void satisfyConstraint()
	{
		Vec3 p1_to_p2 = new Vec3(0,0,0);
		p1_to_p2.f[0] = p2.getPos().f[0]-p1.getPos().f[0]; // vector from p1 to p2
		p1_to_p2.f[1] = p2.getPos().f[1]-p1.getPos().f[1];
		p1_to_p2.f[2] = p2.getPos().f[2]-p1.getPos().f[2];
		float current_distance = p1_to_p2.length(); // current distance between p1 and p2
		Vec3 correctionVector =  new Vec3(0,0,0);
		correctionVector.f[0] = p1_to_p2.f[0]*(1 - rest_distance/current_distance); // The offset vector that could moves p1 into a distance of rest_distance to p2
		correctionVector.f[1] = p1_to_p2.f[1]*(1 - rest_distance/current_distance);
		correctionVector.f[2] = p1_to_p2.f[2]*(1 - rest_distance/current_distance);
		Vec3 correctionVectorHalf = new Vec3(0,0,0); // Lets make it half that length, so that we can move BOTH p1 and p2.
		correctionVectorHalf.f[0] = (float) (correctionVector.f[0]*0.5);
		correctionVectorHalf.f[1] = (float) (correctionVector.f[1]*0.5);
		correctionVectorHalf.f[2] = (float) (correctionVector.f[2]*0.5);
		p1.offsetPos(correctionVectorHalf); // correctionVectorHalf is pointing from p1 to p2, so the length should move p1 half the length needed to satisfy the constraint.
		Vec3 correctionVectorHalf1 = new Vec3(0,0,0);
		correctionVectorHalf1.f[0] = -(float) (correctionVectorHalf.f[0]*0.5);
		correctionVectorHalf1.f[1] = -(float) (correctionVectorHalf.f[1]*0.5);
		correctionVectorHalf1.f[2] = -(float) (correctionVectorHalf.f[2]*0.5);
		p2.offsetPos(correctionVectorHalf1); // we must move p2 the negative direction of correctionVectorHalf since it points from p2 to p1, and not p1 to p2.	
	}
}
