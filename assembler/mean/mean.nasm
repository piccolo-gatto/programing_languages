;use command nasm -felf64 arrays.nasm && ld arrays.o && ./a.out

%macro pushd 0
	push rax
	push rbx
	push rcx
	push rdx
%endmacro

%macro popd 0
	pop rdx
	pop rcx
	pop rbx
	pop rax
%endmacro

%macro print 1
    pushd
    push rdi
    mov rdi, message
    mov rsi, %1
    call printf
    pop rdi
    popd
%endmacro

section   .text
global    main

extern printf

main:   
	mov eax, 0

while:
	cmp eax, [arr_len]
	jz end
	mov ebx, [x+eax*4]
	sub ebx, [y+eax*4]
	mov ecx, [result]
    	add ecx, ebx
	mov [result], ecx
	inc eax
	jmp while
	
end:
	xor eax, eax
    	xor ecx, ecx
    	mov eax, [result]
    	mov ecx, [arr_len]
    	cdq
    	idiv ecx
    	mov [result], eax

    	print [result]

        mov       rax, 60
        xor       rdi, rdi
        syscall

section   .data
	message db "Result: %d", 10, 0
	x dd 5, 3, 2, 6, 1, 7, 4
	y dd 0, 10, 1, 9, 2, 8, 5
	result dq 0
	arr_len db 7
