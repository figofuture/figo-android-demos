package com.figo.cloth;

public class Particle {

	public static final double DAMPING = 0.01;
	public static final double TIME_STEPSIZE2 = 0.5 * 0.5;

	boolean movable = true; // can the particle move or not ? used to pin parts
							// of the cloth

	float mass = 1; // the mass of the particle (is always 1 in this example)
	Vec3 pos; // the current position of the particle in 3D space
	Vec3 old_pos; // the position of the particle in the previous time step,
					// used as part of the verlet numerical integration scheme
	Vec3 acceleration = new Vec3(0, 0, 0); // a vector representing the current
											// acceleration of the particle
	Vec3 accumulated_normal = new Vec3(0, 0, 0); // an accumulated normal (i.e.
													// non normalized), used for
													// OpenGL soft shading

	void addForce(Vec3 f) {
		acceleration.f[0] += f.f[0] / mass;
		acceleration.f[1] += f.f[1] / mass;
		acceleration.f[2] += f.f[2] / mass;
	}

	/*
	 * This is one of the important methods, where the time is progressed a
	 * single step size (TIME_STEPSIZE) The method is called by
	 * Cloth.time_step() Given the equation "force = mass * acceleration" the
	 * next position is found through verlet integration
	 */
	void timeStep() {
		if (movable) {
			Vec3 temp = pos;
			pos.f[0] = (float) (pos.f[0] + (pos.f[0] - old_pos.f[0])
					* (1.0 - DAMPING) + acceleration.f[0] * TIME_STEPSIZE2);
			pos.f[1] = (float) (pos.f[1] + (pos.f[1] - old_pos.f[1])
					* (1.0 - DAMPING) + acceleration.f[1] * TIME_STEPSIZE2);
			pos.f[2] = (float) (pos.f[2] + (pos.f[2] - old_pos.f[2])
					* (1.0 - DAMPING) + acceleration.f[2] * TIME_STEPSIZE2);
			old_pos = temp;
			acceleration = new Vec3(0, 0, 0); // acceleration is reset since it
												// HAS been translated into a
												// change in position (and
												// implicitely into velocity)
		}
	}

	Vec3 getPos() {
		return pos;
	}

	void resetAcceleration() {
		acceleration = new Vec3(0, 0, 0);
	}

	void offsetPos(final Vec3 v) {
		if (movable) {
			pos.f[0] += v.f[0];
			pos.f[1] += v.f[1];
			pos.f[2] += v.f[2];
		}
	}

	void makeUnmovable() {
		movable = false;
	}

	void addToNormal(Vec3 normal) {
		Vec3 vec = normal.normalized();
		accumulated_normal.f[0] += vec.f[0];
		accumulated_normal.f[1] += vec.f[1];
		accumulated_normal.f[2] += vec.f[2];
	}

	Vec3 getNormal() {
		return accumulated_normal;
	} // notice, the normal is not unit length

	void resetNormal() {
		accumulated_normal = new Vec3(0, 0, 0);
	}
}
