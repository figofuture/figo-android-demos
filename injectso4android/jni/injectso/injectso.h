/*
 * injectso.h
 *
 *  Created on:
 *      Author:
 */

#ifndef INJECTSO_H_
#define INJECTSO_H_

#include <sys/ptrace.h>

#ifdef __cplusplus
extern "C"{
#endif

struct Hook{
	char* lib_Path;
	pid_t pid;		
	void* remote_dlopen_addr;	
	void* remote_dlsym_addr;
	void* remote_mmap;
};

int inject_process( pid_t pid, const char *library_path, const char *function_name, void *param, size_t param_size );

void* get_remote_module_addr(pid_t pid,const char* module_name,void* local_func_addr);

int find_pid_by_name( const char *process_name );

#ifdef __cplusplus
}
#endif


#endif /* INJECTSO_H_ */
