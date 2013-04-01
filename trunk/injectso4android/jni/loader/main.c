/*
 * main.c
 *
 *  Created on:
 *      Author:
 */


#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

#include "injectso.h"

void die(int ln,const char* file){
	char temp[256];
	snprintf(temp, sizeof(temp), "failed at %d in %s: %s\n", ln, file,strerror(errno));
	fprintf(stderr, temp);
	exit(-1);
}

static void usage(FILE *fp, const char *self) {
        fprintf(fp, "Usage:\n");
        fprintf(fp, "%s <injectee process name> <inject so file path>\n", self);
}

int main(int argc, char **argv) {

	if(argc != 3) {
		usage(stderr, argv[0]);
		exit(-1);
	}

	pid_t pid = -1;
	pid = find_pid_by_name(argv[1]);
	if ( -1 == pid ) {
		printf("Cannot find pid!\n");
		exit(-1);
	}

	inject_process(pid, argv[2], "hook", "Hook start !!", strlen("Hook start !!") );

	return 0;
}
