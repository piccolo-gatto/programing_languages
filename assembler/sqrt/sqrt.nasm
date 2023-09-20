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

%macro print 2
	pushd
	mov rax, 1
	mov rdi, 1
	mov rdx, %1
	mov rsi, %2
	syscall
	popd
%endmacro

%macro print_num 1
	mov rcx, 10
        mov rbx, 0 
        mov rax, [%1]

	%%divide:
        	xor rdx, rdx
        	div rcx
        	push rdx
        	inc rbx 
        	test rax, rax
        	jnz %%divide

	%%display:
        	pop rax
        	add rax, '0'
        	mov [%1], rax
        	print 1, %1
        	dec rbx
        	cmp rbx, 0
        	jg %%display

        print len, line
%endmacro

%macro iteration 2
	xor rdx, rdx
	mov rax, [%1]
	mov rcx, [%2]
	div rcx
	xor rdx, rdx
	add rax, rcx
	mov rcx, rax
	mov rbx, 2
	div rbx
	mov [x2], rax
%endmacro

section   .text
global    _start

_start:   
	xor rdx, rdx
	mov rax, [num]
	mov rbx, 2 
	div rbx
	mov [x1], rax
	iteration num, x1

while: 
	
	mov rax, [x1]
	sub rax, [x2]
	cmp rax, 1
	jl end

	mov rax, [x2]
    	mov [x1], rax

	xor rax, rax
	xor rdx, rdx

	iteration num, x1
	
	jmp while

end:
	mov rax, [x2]
	print len1, message1
	print_num num
	print len2, message2
	print_num x2
        mov       rax, 60
        xor       rdi, rdi
        syscall

section   .data
	message1 db "Number: "
	len1 equ $ - message1
	message2 db "Sqrt for number: "
	len2 equ $ - message2
	num dq 441
	line db 0xA, 0xD
	len equ $ - line
	result db 0

section .bss
	x1 resq 1
	x2 resq 1
