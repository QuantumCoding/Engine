/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef GLubyte * (APIENTRY *glGetStringiPROC) (GLenum name, GLuint index);
typedef void (APIENTRY *glClearBufferfvPROC) (GLenum buffer, GLint drawbuffer, const GLfloat * value);
typedef void (APIENTRY *glClearBufferivPROC) (GLenum buffer, GLint drawbuffer, const GLint * value);
typedef void (APIENTRY *glClearBufferuivPROC) (GLenum buffer, GLint drawbuffer, const GLint * value);
typedef void (APIENTRY *glClearBufferfiPROC) (GLenum buffer, GLint drawbuffer, GLfloat depth, GLint stencil);
typedef void (APIENTRY *glVertexAttribI1iPROC) (GLuint index, GLint x);
typedef void (APIENTRY *glVertexAttribI2iPROC) (GLuint index, GLint x, GLint y);
typedef void (APIENTRY *glVertexAttribI3iPROC) (GLuint index, GLint x, GLint y, GLint z);
typedef void (APIENTRY *glVertexAttribI4iPROC) (GLuint index, GLint x, GLint y, GLint z, GLint w);
typedef void (APIENTRY *glVertexAttribI1uiPROC) (GLuint index, GLuint x);
typedef void (APIENTRY *glVertexAttribI2uiPROC) (GLuint index, GLuint x, GLuint y);
typedef void (APIENTRY *glVertexAttribI3uiPROC) (GLuint index, GLuint x, GLuint y, GLuint z);
typedef void (APIENTRY *glVertexAttribI4uiPROC) (GLuint index, GLuint x, GLuint y, GLuint z, GLuint w);
typedef void (APIENTRY *glVertexAttribI1ivPROC) (GLuint index, const GLint * v);
typedef void (APIENTRY *glVertexAttribI2ivPROC) (GLuint index, const GLint * v);
typedef void (APIENTRY *glVertexAttribI3ivPROC) (GLuint index, const GLint * v);
typedef void (APIENTRY *glVertexAttribI4ivPROC) (GLuint index, const GLint * v);
typedef void (APIENTRY *glVertexAttribI1uivPROC) (GLuint index, const GLuint * v);
typedef void (APIENTRY *glVertexAttribI2uivPROC) (GLuint index, const GLuint * v);
typedef void (APIENTRY *glVertexAttribI3uivPROC) (GLuint index, const GLuint * v);
typedef void (APIENTRY *glVertexAttribI4uivPROC) (GLuint index, const GLuint * v);
typedef void (APIENTRY *glVertexAttribI4bvPROC) (GLuint index, const GLbyte * v);
typedef void (APIENTRY *glVertexAttribI4svPROC) (GLuint index, const GLshort * v);
typedef void (APIENTRY *glVertexAttribI4ubvPROC) (GLuint index, const GLubyte * v);
typedef void (APIENTRY *glVertexAttribI4usvPROC) (GLuint index, const GLushort * v);
typedef void (APIENTRY *glVertexAttribIPointerPROC) (GLuint index, GLint size, GLenum type, GLsizei stride, const GLvoid * buffer);
typedef void (APIENTRY *glGetVertexAttribIivPROC) (GLuint index, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetVertexAttribIuivPROC) (GLuint index, GLenum pname, GLuint * params);
typedef void (APIENTRY *glUniform1uiPROC) (GLint location, GLuint v0);
typedef void (APIENTRY *glUniform2uiPROC) (GLint location, GLuint v0, GLuint v1);
typedef void (APIENTRY *glUniform3uiPROC) (GLint location, GLuint v0, GLuint v1, GLuint v2);
typedef void (APIENTRY *glUniform4uiPROC) (GLint location, GLuint v0, GLuint v1, GLuint v2, GLuint v3);
typedef void (APIENTRY *glUniform1uivPROC) (GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glUniform2uivPROC) (GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glUniform3uivPROC) (GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glUniform4uivPROC) (GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glGetUniformuivPROC) (GLuint program, GLint location, GLuint * params);
typedef void (APIENTRY *glBindFragDataLocationPROC) (GLuint program, GLuint colorNumber, const GLchar * name);
typedef GLint (APIENTRY *glGetFragDataLocationPROC) (GLuint program, const GLchar * name);
typedef void (APIENTRY *glBeginConditionalRenderPROC) (GLuint id, GLenum mode);
typedef void (APIENTRY *glEndConditionalRenderPROC) ();
typedef GLvoid * (APIENTRY *glMapBufferRangePROC) (GLenum target, GLintptr offset, GLsizeiptr length, GLbitfield access);
typedef void (APIENTRY *glFlushMappedBufferRangePROC) (GLenum target, GLintptr offset, GLsizeiptr length);
typedef void (APIENTRY *glClampColorPROC) (GLenum target, GLenum clamp);
typedef GLboolean (APIENTRY *glIsRenderbufferPROC) (GLuint renderbuffer);
typedef void (APIENTRY *glBindRenderbufferPROC) (GLenum target, GLuint renderbuffer);
typedef void (APIENTRY *glDeleteRenderbuffersPROC) (GLint n, const GLuint * renderbuffers);
typedef void (APIENTRY *glGenRenderbuffersPROC) (GLint n, GLuint * renderbuffers);
typedef void (APIENTRY *glRenderbufferStoragePROC) (GLenum target, GLenum internalformat, GLsizei width, GLsizei height);
typedef void (APIENTRY *glGetRenderbufferParameterivPROC) (GLenum target, GLenum pname, GLint * params);
typedef GLboolean (APIENTRY *glIsFramebufferPROC) (GLuint framebuffer);
typedef void (APIENTRY *glBindFramebufferPROC) (GLenum target, GLuint framebuffer);
typedef void (APIENTRY *glDeleteFramebuffersPROC) (GLint n, const GLuint * framebuffers);
typedef void (APIENTRY *glGenFramebuffersPROC) (GLint n, GLuint * framebuffers);
typedef GLenum (APIENTRY *glCheckFramebufferStatusPROC) (GLenum target);
typedef void (APIENTRY *glFramebufferTexture1DPROC) (GLenum target, GLenum attachment, GLenum textarget, GLuint texture, GLint level);
typedef void (APIENTRY *glFramebufferTexture2DPROC) (GLenum target, GLenum attachment, GLenum textarget, GLuint texture, GLint level);
typedef void (APIENTRY *glFramebufferTexture3DPROC) (GLenum target, GLenum attachment, GLenum textarget, GLuint texture, GLint level, GLint zoffset);
typedef void (APIENTRY *glFramebufferRenderbufferPROC) (GLenum target, GLenum attachment, GLenum renderbuffertarget, GLuint renderbuffer);
typedef void (APIENTRY *glGetFramebufferAttachmentParameterivPROC) (GLenum target, GLenum attachment, GLenum pname, GLint * params);
typedef void (APIENTRY *glGenerateMipmapPROC) (GLenum target);
typedef void (APIENTRY *glRenderbufferStorageMultisamplePROC) (GLenum target, GLsizei samples, GLenum internalformat, GLsizei width, GLsizei height);
typedef void (APIENTRY *glBlitFramebufferPROC) (GLint srcX0, GLint srcY0, GLint srcX1, GLint srcY1, GLint dstX0, GLint dstY0, GLint dstX1, GLint dstY1, GLbitfield mask, GLenum filter);
typedef void (APIENTRY *glTexParameterIivPROC) (GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glTexParameterIuivPROC) (GLenum target, GLenum pname, GLuint * params);
typedef void (APIENTRY *glGetTexParameterIivPROC) (GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetTexParameterIuivPROC) (GLenum target, GLenum pname, GLuint * params);
typedef void (APIENTRY *glFramebufferTextureLayerPROC) (GLenum target, GLenum attachment, GLuint texture, GLint level, GLint layer);
typedef void (APIENTRY *glColorMaskiPROC) (GLuint buf, GLboolean r, GLboolean g, GLboolean b, GLboolean a);
typedef void (APIENTRY *glGetBooleani_vPROC) (GLenum value, GLuint index, GLboolean * data);
typedef void (APIENTRY *glGetIntegeri_vPROC) (GLenum value, GLuint index, GLint * data);
typedef void (APIENTRY *glEnableiPROC) (GLenum target, GLuint index);
typedef void (APIENTRY *glDisableiPROC) (GLenum target, GLuint index);
typedef GLboolean (APIENTRY *glIsEnablediPROC) (GLenum target, GLuint index);
typedef void (APIENTRY *glBindBufferRangePROC) (GLenum target, GLuint index, GLuint buffer, GLintptr offset, GLsizeiptr size);
typedef void (APIENTRY *glBindBufferBasePROC) (GLenum target, GLuint index, GLuint buffer);
typedef void (APIENTRY *glBeginTransformFeedbackPROC) (GLenum primitiveMode);
typedef void (APIENTRY *glEndTransformFeedbackPROC) ();
typedef void (APIENTRY *glTransformFeedbackVaryingsPROC) (GLuint program, GLsizei count, const GLchar ** varyings, GLenum bufferMode);
typedef void (APIENTRY *glGetTransformFeedbackVaryingPROC) (GLuint program, GLuint index, GLsizei bufSize, GLsizei * length, GLsizei * size, GLenum * type, GLchar * name);
typedef void (APIENTRY *glBindVertexArrayPROC) (GLuint array);
typedef void (APIENTRY *glDeleteVertexArraysPROC) (GLsizei n, const GLuint * arrays);
typedef void (APIENTRY *glGenVertexArraysPROC) (GLsizei n, GLuint * arrays);
typedef GLboolean (APIENTRY *glIsVertexArrayPROC) (GLuint array);

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_GL30_nglGetStringi(JNIEnv *env, jclass clazz, jint name, jint index, jlong function_pointer) {
	glGetStringiPROC glGetStringi = (glGetStringiPROC)((intptr_t)function_pointer);
	GLubyte * __result = glGetStringi(name, index);
	return NewStringNativeUnsigned(env, __result);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglClearBufferfv(JNIEnv *env, jclass clazz, jint buffer, jint drawbuffer, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glClearBufferfvPROC glClearBufferfv = (glClearBufferfvPROC)((intptr_t)function_pointer);
	glClearBufferfv(buffer, drawbuffer, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglClearBufferiv(JNIEnv *env, jclass clazz, jint buffer, jint drawbuffer, jlong value, jlong function_pointer) {
	const GLint *value_address = (const GLint *)(intptr_t)value;
	glClearBufferivPROC glClearBufferiv = (glClearBufferivPROC)((intptr_t)function_pointer);
	glClearBufferiv(buffer, drawbuffer, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglClearBufferuiv(JNIEnv *env, jclass clazz, jint buffer, jint drawbuffer, jlong value, jlong function_pointer) {
	const GLint *value_address = (const GLint *)(intptr_t)value;
	glClearBufferuivPROC glClearBufferuiv = (glClearBufferuivPROC)((intptr_t)function_pointer);
	glClearBufferuiv(buffer, drawbuffer, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglClearBufferfi(JNIEnv *env, jclass clazz, jint buffer, jint drawbuffer, jfloat depth, jint stencil, jlong function_pointer) {
	glClearBufferfiPROC glClearBufferfi = (glClearBufferfiPROC)((intptr_t)function_pointer);
	glClearBufferfi(buffer, drawbuffer, depth, stencil);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI1i(JNIEnv *env, jclass clazz, jint index, jint x, jlong function_pointer) {
	glVertexAttribI1iPROC glVertexAttribI1i = (glVertexAttribI1iPROC)((intptr_t)function_pointer);
	glVertexAttribI1i(index, x);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI2i(JNIEnv *env, jclass clazz, jint index, jint x, jint y, jlong function_pointer) {
	glVertexAttribI2iPROC glVertexAttribI2i = (glVertexAttribI2iPROC)((intptr_t)function_pointer);
	glVertexAttribI2i(index, x, y);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI3i(JNIEnv *env, jclass clazz, jint index, jint x, jint y, jint z, jlong function_pointer) {
	glVertexAttribI3iPROC glVertexAttribI3i = (glVertexAttribI3iPROC)((intptr_t)function_pointer);
	glVertexAttribI3i(index, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4i(JNIEnv *env, jclass clazz, jint index, jint x, jint y, jint z, jint w, jlong function_pointer) {
	glVertexAttribI4iPROC glVertexAttribI4i = (glVertexAttribI4iPROC)((intptr_t)function_pointer);
	glVertexAttribI4i(index, x, y, z, w);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI1ui(JNIEnv *env, jclass clazz, jint index, jint x, jlong function_pointer) {
	glVertexAttribI1uiPROC glVertexAttribI1ui = (glVertexAttribI1uiPROC)((intptr_t)function_pointer);
	glVertexAttribI1ui(index, x);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI2ui(JNIEnv *env, jclass clazz, jint index, jint x, jint y, jlong function_pointer) {
	glVertexAttribI2uiPROC glVertexAttribI2ui = (glVertexAttribI2uiPROC)((intptr_t)function_pointer);
	glVertexAttribI2ui(index, x, y);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI3ui(JNIEnv *env, jclass clazz, jint index, jint x, jint y, jint z, jlong function_pointer) {
	glVertexAttribI3uiPROC glVertexAttribI3ui = (glVertexAttribI3uiPROC)((intptr_t)function_pointer);
	glVertexAttribI3ui(index, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4ui(JNIEnv *env, jclass clazz, jint index, jint x, jint y, jint z, jint w, jlong function_pointer) {
	glVertexAttribI4uiPROC glVertexAttribI4ui = (glVertexAttribI4uiPROC)((intptr_t)function_pointer);
	glVertexAttribI4ui(index, x, y, z, w);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI1iv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLint *v_address = (const GLint *)(intptr_t)v;
	glVertexAttribI1ivPROC glVertexAttribI1iv = (glVertexAttribI1ivPROC)((intptr_t)function_pointer);
	glVertexAttribI1iv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI2iv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLint *v_address = (const GLint *)(intptr_t)v;
	glVertexAttribI2ivPROC glVertexAttribI2iv = (glVertexAttribI2ivPROC)((intptr_t)function_pointer);
	glVertexAttribI2iv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI3iv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLint *v_address = (const GLint *)(intptr_t)v;
	glVertexAttribI3ivPROC glVertexAttribI3iv = (glVertexAttribI3ivPROC)((intptr_t)function_pointer);
	glVertexAttribI3iv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4iv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLint *v_address = (const GLint *)(intptr_t)v;
	glVertexAttribI4ivPROC glVertexAttribI4iv = (glVertexAttribI4ivPROC)((intptr_t)function_pointer);
	glVertexAttribI4iv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI1uiv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLuint *v_address = (const GLuint *)(intptr_t)v;
	glVertexAttribI1uivPROC glVertexAttribI1uiv = (glVertexAttribI1uivPROC)((intptr_t)function_pointer);
	glVertexAttribI1uiv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI2uiv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLuint *v_address = (const GLuint *)(intptr_t)v;
	glVertexAttribI2uivPROC glVertexAttribI2uiv = (glVertexAttribI2uivPROC)((intptr_t)function_pointer);
	glVertexAttribI2uiv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI3uiv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLuint *v_address = (const GLuint *)(intptr_t)v;
	glVertexAttribI3uivPROC glVertexAttribI3uiv = (glVertexAttribI3uivPROC)((intptr_t)function_pointer);
	glVertexAttribI3uiv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4uiv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLuint *v_address = (const GLuint *)(intptr_t)v;
	glVertexAttribI4uivPROC glVertexAttribI4uiv = (glVertexAttribI4uivPROC)((intptr_t)function_pointer);
	glVertexAttribI4uiv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4bv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLbyte *v_address = (const GLbyte *)(intptr_t)v;
	glVertexAttribI4bvPROC glVertexAttribI4bv = (glVertexAttribI4bvPROC)((intptr_t)function_pointer);
	glVertexAttribI4bv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4sv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLshort *v_address = (const GLshort *)(intptr_t)v;
	glVertexAttribI4svPROC glVertexAttribI4sv = (glVertexAttribI4svPROC)((intptr_t)function_pointer);
	glVertexAttribI4sv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4ubv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLubyte *v_address = (const GLubyte *)(intptr_t)v;
	glVertexAttribI4ubvPROC glVertexAttribI4ubv = (glVertexAttribI4ubvPROC)((intptr_t)function_pointer);
	glVertexAttribI4ubv(index, v_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglVertexAttribI4usv(JNIEnv *env, jclass clazz, jint index, jlong v, jlong function_pointer) {
	const GLushort *v_address = (const GLushort *)(intptӲ*��^�\t�mE�#.A*ř6�w��n����$�j�P�AngZ�����/�6�u�Z[!\���4��q#*L��$<��?w�8�0 �!�%�`񧒍���*D�>�9ŗ��ee�L��UyωT=���/��(�ƎU0�{7E��9W�}�e��G�	e�,<7~�5e�q�������Ռ�1U�w��N凞F� ��h���c��s���No�O%iJdZ��|�æ{��.����j���UJS���k��}�_����k�U�M}�	@�L��)!�	��bK��(����ZD忹���8i�E,Ʒo��bQ:?\�� ~T��U��:
�����T�',x�V���\�%��k�����:B%�t�8�0/%�A �j��
�S���<7��;?vt�=� U�W�j1�4I���4Ϥ6�E��{`2�<�'�M�L��[��+v�ȣ�ؐ���޴y3Rٱj���	���Z5#��k>��-�9��j���� Z��.��l��!�nz�\[V�̟QR�BA��=�jo��:�J#����]Өc,J����9}�@~�dw4P�b�)α�6���]��g?��I��j�j/�u1S5	UVs0l�����w�ʗF�w�]��"㩱�E��4ι��Ѵ��N;�u{h�Hm}"��S����}?�RQ�c]���`J���^�P*$욦�斦������1�N����P~"�\�a\��DaՀ�<��:9�<ֹ�7<Ă���˝�9еHR��n���)��
������ 263%�^�F����i�+JzI�h"�&D  8c���ќ��%�Xz�Mu����5+aiI8���j��ZȜiܭ�B��'����i� ��Q������l,���0���z��kW&pz��_	GR��P�����BawjQJ��Ruf��*0��Yl5�9q����m.,����'�%��i�c��:hC�sn��e~�n�J�o��✙�`(���U&���f�Jh2��/T�׿fHԍw`YSç�3D�v��6ZD���H���Նk\ZQؗ�	9~��	�1��B��ITEE��@�X���QЄj�X�_	�_tE��n$7�3 ��D��k�#p�ޯ��'Z�����ٶzu�)-zȺ+A\�ߜ�����C�B��C�yN[��~��z)oV�`|'=����ӵH��L�$oX*��I;�шU�ڞp͠�-�.�T®��������J2�#x-u/�#j�X#%�P^��Y~�f����A�h�E�g�ɺ�T�΋��+�<]㹆�1`��-ተ<��uFpA:
c�f��Py�w�qJl����Gp�l��i1� ���qwp�:�%#��Q�)��-�F�!o/��H�\���НZnp�f�R��2�~��g����	B���7"�_$.z��JC��܈t傘Q�G,�l<��k�T�`���nF��b�l_횽����T�j�o�Y�@g 1��l�r������s��K���px��w�i#�����d%]��1�/=pj3��+u"��H%/�l�%���4x,�i��-Q�!���D�ƨd�!e-�8�c��s��]H\��inxo��tV%Nc������\������:�(���z�q#?�F|
R�6���#e��� ��#y:!�^"��
�R�]3��<�B��H��f�~wR�pDT���߁�WX�W�Cψ7��E��8�8�� �����Jw�p��~��k�'C�8�t�+>F����sv�:?������E=ZD�����h�_��G5(��
���7��@	�W�����Y�����1S�wܫگc{;%�P�%���#�h�Qי�����!@h���>�:�!��<�����4��v��J���J�<AM��A0���7I!�^װ�7���M�`�-N.�4�.��Kl��SU9���e*J��4 �N��H�P���N��\	 �>@*�	��=H���۪ZK�w�$��4�G"�|����a�$'&�����uW`$T,?^����%��U�e��%4�J^��!z��	jU��L�J(=�7d���bPu��bGy 9�4�t�$2X�~kV�t��<�:�W�o���О��o�^%f6$a�X�;�ҨJ�7`֙0h;�_��?^����q^���=��������0dd���l�2$��+�C�`��� q�r��Cw�r����}/�^45T�ը���V�D�./4�5��K;��6l����A�B�ky���`B>�R-�B��}�R:��ث�A��##�����<�x���1Y��~�5������=��l�����ѴX{�bCV�IU�cbDԳ�]N��?�=��(�勵׽�rZ_��y�f��tY�.%~��B	�Rڵ�Vq���*� x�X�n���;m�l�=�Y���& �kW	��_������Ѭ�Сz<P�5���yuFڝG~��n'��Ӓi��)��u!;����_�B��Y>��A��.e�N��?وarΌ�#�� �W�*ｾ��������řn6o��N�&��"o��aM����*�;B]��� [�YR�a�	�W�v�8|���[| ���֋�z�{��'Y0�%w��>��1 h2���f=�!\���"�s#j���+^��">��@�掀����45�\�3}�Cu�e-����^���ڡq�F�VQX)�|ǘ�1�_]3 ����X=@�i8镴�_��Z8J)J炋Yp����Km�Q�nXX�=�cxٓA�X�<A�wh`w�3�GHdY���M�.)��>����n��=�waSO����O#��1�G����%2/�xG,�0��z���&6 �On�qV��o��;�U����+��]���ԫ�P��
����R-F�,B�)~3�
���a�.��h=�GSX5#I�&��)�=?�5i���کw#�X��:�i�}���~�tO��N+]�}rFz�5� ��h۳`�Qd��]@����6��	;dt��Xa��x�M�0��;�J�+��~��� ����n�>��u��Ҫ|�Ɨ� �\|0��H(�C<��ϯ��8��/�� ��-��ǃ�o�UYS�����M���9�1���b����~�}s6Z��K��=k|�ۼmk�s�v"��h�'�l�ĆZ[w������)ƿ|��� �׃k����\�b��CS�=;���D������qf��&��<���Yr�"�����#��ai�j��o�#����축P�̍�!Qe:�3�	�3=  nӪ3s��M�D�d4��X���[~�j_�{bo�G�Q��c�q@܃�4����4�<��ʊ;W$	
,.ވ/�`��	v,J�j�͚�4�C%±l,[�Zk��8H#.��v�wK�A+jL�����z��r����( �^�]R����0Ij ]z@Vva����V'~hD=d���*��m;�3>�=�=喿���_��/S����m�է�X��߽��H�7����Х����"�0ѧ��1��V��|y`�Ԋ>�Eަ�<p�����&�i}�z"���$Ѡ����*{�#w��G[M�����2�"�/��(q�v��*��X�Nm�R&��cO��]�m?\�[�8���%�F�Fǎ¬#���dy[�rn��t0u��-a��J���d�	�ƿ)=��w|����n��"Y/t�X3Mj��K�I7���2�یz*��v�3Ae��C[�� ���FtR���,�؏�R?>S�8 �:�;g_�S�E�{u~�v;o�%�E}���v1=Ґ �/L��	oT88���Y(�
��|`��©yӣU�P�"���%ɕ<��*�U�d˓�gb��Q����r/ī����
 Qh�S��t�<Q��7"�7���Б�ta%3�zYR�	xiU��~Q��F��.��Ш�>Rḵ��۞�W��IYɺwMO(�px=��!�� !LA�����B:
��'1N�C�KQv�{xC���9��<�T�AJt�^s��EX���/�F��3 ���s� �A		�h+x�W��I��c��jɻw�y!�m��N�U�=	�[�%w@�?���Z��\��.ݤ��3�� ޡ����W�c��˛,h	��̟��� Xo�ň���:Jy)��V��0G$��Ӡ;ذy���/��*V��Q]�4�J�oy;���o}�6�t�Ro*Fñ/�����o�Q�_ٝ���;�����"n�*w�����wUlz_��Yd��Gsӽ4)[�/����G�_o,r�^��2Ө���K�0�+����qDM�#���'a�p��?rF�j,�!���+qҍ�P�\�|�1�t�p?�}]�}i̫J�t'4��[!RJ�������Q��5���E�-��${��!�ڭ�rA���(1U$�E� ����-\JB���t�@���N���9�/Ow%��xYظܕ��0W_͏}���{��>ڐց]2R����P��`�\d4FE���5�
S�T`�R·(��[l��,&Q1"��Ї�)�ή(DLe�螺�n�-~a�X$��$��+��7I��Σu��x���B*_S�������������0�<\��2�?��M	AqV�P�D��1�Z��=�_�������y�QLG�,�R�9�+}|O�*���裳�)����6z�Zo��&��"_�MNXcŉv�o��fâ�/C�Z�/@zr�yG��,�`�ɬ��cnin��*��;�+@�P78{e�K� �������ЀC���.Ur^p�w��l��و���L����|�O��U�B��l(u1$����&i���y<�67  '�� ?zv�I@�)��.�]�Y�������%a�Iͽ+�@�����9��\��>z2��u_���Įd�K˹/*�2܊Q(�B<؈X�{ݽ� msR8��)�eWX5_'i;_���r�� ��c����m�"��QRmX��
f�IW����Z��U�h5�Q�AP�w�cǼ�#��Rxd�?��ž����7˹Î*��x5�.�
��p�,��_���q��S����O�4��y�K1�v��sr�k���vР����J f�w/�
u+�+J����1%����� '��n{ȇ�s1��`7@���	�(L�v�SzW3�П�w8�M]M���m8K�9�����Gj�-$�>���A��	��4;[u�b�%
`m�[�m'���\�`3_&�Rkl�g���'����,��!oI7.L�[�V��W_FG�R�ެ�5-�[�.ŝ/��I�pKWnaO(�[Ā5#P$P�wR���H2�)��qL¨i��%��g �S5Z�W��kb҂+ܛW{:)�UI���KNp�Ni�lA�?v�)�_tϦ0fN4h 
�8
d�$��L'M������mbg�����&����qO�tar%���W�3���|ֲ4u9y)�*�봊C���DV���gˏ;�2����W����l�Z�v��>�GN=saY�|��])��+��:�+Ȳk��	>�� 	ӾGa,��X��s�]P4�b�F�#s�v�xz?�ek��oQ�v���� E��.��{�s�W8��CJ:���D��n6�t%�J;�(��w�Z�1s�="�m�*
Im�b_��2�8<7I��*�e
t�Ů��tX=�����J��}�¢zi-��WfOI���<�P�?:��4l��7�E��U����0�#��p���4�$h��D@�$��!ɍ����Q��$��~�/4m��8��}��垬6��D|D1���KE^�C5B$�~��<T���c̙fO<��è�.5�n����0"�aW��L�;/Y������:ވUv3G�ݍjz�xt[v��k���sGx٦��IH�lݽ;��0���3��/7Ȗ�i�O]��	�!c�ͺ0�?o��M6�Z@[��(���47,��?6��8��\wv3[�D���nJ�σ���*�Y�ԧ�P���˝�ϰ+�(�n�ruL
�����g��{�|U萝��t4�ṾS=��/L��2	�V,���E&x�I��\�)�Ƒ=��7[�M^u��Y�c����$L��[�?8D5��a���9Mج��"�!�;�����)��PH8${�����9P�4�.N�	"�}tsf�G�iJ��"��|��S�?�M�yH�o���P���z�e�T���� ���;�&\�����G�L� ��j�}�N��0�!�p�ksI���h�Ӆ_$s�
�F*�Q�
&�!pN�KѨ�N�$�	��+~`��� ���Y�)tl�@�%�nZ���y��t[���XZ��Z߃K=���Gf�@�#d����C�s�t:6:N�����7l�0��O�y&/v>��.���|ؕ.��e?��0�W!��� �(�l�\�Ijc�ΐ�1���$>�辦t��J5q�E��og�J����zqJ���h�-��;�^0�#��8�\��'���\`l?h�n�~ �m��?�w���f
	���
-��ȱ	��hV��rՊk2�3�OZ!z��r_�l_��^g��z�Pi�&����N8�|6���xDݒv,s%$�ĕ�L�����c����܌�u�e�v��v��_
寽��q1B�]awL�]���[���cO��]� ( �`��A�#@x!=;�d	��R�?6_�"�&k_�ʈrc���2�&Fݓ���ȟ;���6~n��x�t�tg��YM��S�S`ei,�y'x���S=@cd�c�����CM�+�ň�̓��-L��^���q?â�����H�Zt�WR\��i�@�曒C�&'tG�q6�`U�ԯ��ݢ��ѨU#��Q����'����qx%����:ڇ,��=��� ������K�z��u�.7�ėE�*�c)�I�bC��gzv �t�$<��q�����I_fE6�a�WE�N�r��T�ʫJ�ø_�]�*�$��X���	UY�>����
����8G�cۊ�\�O=���U�bL���"��W���`pS��d'ND�̣/E��uA�\�&,�G.�\�b�C�u�a�1�sqn˨g�ȉ�o�Ԗ@�s5]8��?�H�~��V����X���s���X^:"0
�;pf=-ٚ�D�����x��k��2�h?��KqĜ�^��Kx0}���'���惁�0|'0V�潣�:�������[S��a��S�6Wk�׳A*w4��,���
 =�c��ܔ��^�p7��D�O�a�)VR�D+�jq�:�� ����'
 ���������}B��b��}����	q�N�@`Q�蜓��~ g��Y}ӯ�i�n�m�$S?̤�\Pc��	�;U0��4��nI��3Q���)a#�W�9{����;�Z�ek��X�r��6�L��x����NWXnx#��@�R.����1��,��E�g�D�"���k�Y���75&.��Pc?�y�"_:�]j k�a�Z�(�ߴ]J#V��oͫ���Mir\�:�w��*�ҵ�7u;`�)9\�9����,d�7���x_��O[�E=+���?�����dOΣ�F�M&��f�Bc�|����]�����l詤�<a��`g+6���?~]���Z��ƯW$D�p��qnN�:�<׋x���AV�<��]����F�xvS�$�r��cFeL����r��V���prH5Bv'd��'u�r ��W��۪��Z.�C���X�y'���g
�gvl��[]�S��?b�	lU��D rnEqkb�n�����e]l7Gk4�X��+1	�y�cL���)	7a�Ò�3�Ո��_�ʟK�(,3�:M��ۧi����R�Egy��r>�nN�[͢VTp��fgV��~M��\�`�:��kn\����}����q_��cz��c�WĶo&^��67o��"U��(� �@o	5�/�4ug��c��ד#��.�D�9�0o�V�������}�s�P�Ϟ����m و�Z�`ku��a���V|�tr_t)function_pointer);
	glDeleteRenderbuffers(n, renderbuffers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGenRenderbuffers(JNIEnv *env, jclass clazz, jint n, jlong renderbuffers, jlong function_pointer) {
	GLuint *renderbuffers_address = (GLuint *)(intptr_t)renderbuffers;
	glGenRenderbuffersPROC glGenRenderbuffers = (glGenRenderbuffersPROC)((intptr_t)function_pointer);
	glGenRenderbuffers(n, renderbuffers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglRenderbufferStorage(JNIEnv *env, jclass clazz, jint target, jint internalformat, jint width, jint height, jlong function_pointer) {
	glRenderbufferStoragePROC glRenderbufferStorage = (glRenderbufferStoragePROC)((intptr_t)function_pointer);
	glRenderbufferStorage(target, internalformat, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetRenderbufferParameteriv(JNIEnv *env, jclass clazz, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetRenderbufferParameterivPROC glGetRenderbufferParameteriv = (glGetRenderbufferParameterivPROC)((intptr_t)function_pointer);
	glGetRenderbufferParameteriv(target, pname, params_address);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_GL30_nglIsFramebuffer(JNIEnv *env, jclass clazz, jint framebuffer, jlong function_pointer) {
	glIsFramebufferPROC glIsFramebuffer = (glIsFramebufferPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsFramebuffer(framebuffer);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglBindFramebuffer(JNIEnv *env, jclass clazz, jint target, jint framebuffer, jlong function_pointer) {
	glBindFramebufferPROC glBindFramebuffer = (glBindFramebufferPROC)((intptr_t)function_pointer);
	glBindFramebuffer(target, framebuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglDeleteFramebuffers(JNIEnv *env, jclass clazz, jint n, jlong framebuffers, jlong function_pointer) {
	const GLuint *framebuffers_address = (const GLuint *)(intptr_t)framebuffers;
	glDeleteFramebuffersPROC glDeleteFramebuffers = (glDeleteFramebuffersPROC)((intptr_t)function_pointer);
	glDeleteFramebuffers(n, framebuffers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGenFramebuffers(JNIEnv *env, jclass clazz, jint n, jlong framebuffers, jlong function_pointer) {
	GLuint *framebuffers_address = (GLuint *)(intptr_t)framebuffers;
	glGenFramebuffersPROC glGenFramebuffers = (glGenFramebuffersPROC)((intptr_t)function_pointer);
	glGenFramebuffers(n, framebuffers_address);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_GL30_nglCheckFramebufferStatus(JNIEnv *env, jclass clazz, jint target, jlong function_pointer) {
	glCheckFramebufferStatusPROC glCheckFramebufferStatus = (glCheckFramebufferStatusPROC)((intptr_t)function_pointer);
	GLenum __result = glCheckFramebufferStatus(target);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglFramebufferTexture1D(JNIEnv *env, jclass clazz, jint target, jint attachment, jint textarget, jint texture, jint level, jlong function_pointer) {
	glFramebufferTexture1DPROC glFramebufferTexture1D = (glFramebufferTexture1DPROC)((intptr_t)function_pointer);
	glFramebufferTexture1D(target, attachment, textarget, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglFramebufferTexture2D(JNIEnv *env, jclass clazz, jint target, jint attachment, jint textarget, jint texture, jint level, jlong function_pointer) {
	glFramebufferTexture2DPROC glFramebufferTexture2D = (glFramebufferTexture2DPROC)((intptr_t)function_pointer);
	glFramebufferTexture2D(target, attachment, textarget, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglFramebufferTexture3D(JNIEnv *env, jclass clazz, jint target, jint attachment, jint textarget, jint texture, jint level, jint zoffset, jlong function_pointer) {
	glFramebufferTexture3DPROC glFramebufferTexture3D = (glFramebufferTexture3DPROC)((intptr_t)function_pointer);
	glFramebufferTexture3D(target, attachment, textarget, texture, level, zoffset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglFramebufferRenderbuffer(JNIEnv *env, jclass clazz, jint target, jint attachment, jint renderbuffertarget, jint renderbuffer, jlong function_pointer) {
	glFramebufferRenderbufferPROC glFramebufferRenderbuffer = (glFramebufferRenderbufferPROC)((intptr_t)function_pointer);
	glFramebufferRenderbuffer(target, attachment, renderbuffertarget, renderbuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetFramebufferAttachmentParameteriv(JNIEnv *env, jclass clazz, jint target, jint attachment, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetFramebufferAttachmentParameterivPROC glGetFramebufferAttachmentParameteriv = (glGetFramebufferAttachmentParameterivPROC)((intptr_t)function_pointer);
	glGetFramebufferAttachmentParameteriv(target, attachment, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGenerateMipmap(JNIEnv *env, jclass clazz, jint target, jlong function_pointer) {
	glGenerateMipmapPROC glGenerateMipmap = (glGenerateMipmapPROC)((intptr_t)function_pointer);
	glGenerateMipmap(target);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglRenderbufferStorageMultisample(JNIEnv *env, jclass clazz, jint target, jint samples, jint internalformat, jint width, jint height, jlong function_pointer) {
	glRenderbufferStorageMultisamplePROC glRenderbufferStorageMultisample = (glRenderbufferStorageMultisamplePROC)((intptr_t)function_pointer);
	glRenderbufferStorageMultisample(target, samples, internalformat, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglBlitFramebuffer(JNIEnv *env, jclass clazz, jint srcX0, jint srcY0, jint srcX1, jint srcY1, jint dstX0, jint dstY0, jint dstX1, jint dstY1, jint mask, jint filter, jlong function_pointer) {
	glBlitFramebufferPROC glBlitFramebuffer = (glBlitFramebufferPROC)((intptr_t)function_pointer);
	glBlitFramebuffer(srcX0, srcY0, srcX1, srcY1, dstX0, dstY0, dstX1, dstY1, mask, filter);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglTexParameterIiv(JNIEnv *env, jclass clazz, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glTexParameterIivPROC glTexParameterIiv = (glTexParameterIivPROC)((intptr_t)function_pointer);
	glTexParameterIiv(target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglTexParameterIuiv(JNIEnv *env, jclass clazz, jint target, jint pname, jlong params, jlong function_pointer) {
	GLuint *params_address = (GLuint *)(intptr_t)params;
	glTexParameterIuivPROC glTexParameterIuiv = (glTexParameterIuivPROC)((intptr_t)function_pointer);
	glTexParameterIuiv(target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetTexParameterIiv(JNIEnv *env, jclass clazz, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetTexParameterIivPROC glGetTexParameterIiv = (glGetTexParameterIivPROC)((intptr_t)function_pointer);
	glGetTexParameterIiv(target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetTexParameterIuiv(JNIEnv *env, jclass clazz, jint target, jint pname, jlong params, jlong function_pointer) {
	GLuint *params_address = (GLuint *)(intptr_t)params;
	glGetTexParameterIuivPROC glGetTexParameterIuiv = (glGetTexParameterIuivPROC)((intptr_t)function_pointer);
	glGetTexParameterIuiv(target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglFramebufferTextureLayer(JNIEnv *env, jclass clazz, jint target, jint attachment, jint texture, jint level, jint layer, jlong function_pointer) {
	glFramebufferTextureLayerPROC glFramebufferTextureLayer = (glFramebufferTextureLayerPROC)((intptr_t)function_pointer);
	glFramebufferTextureLayer(target, attachment, texture, level, layer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglColorMaski(JNIEnv *env, jclass clazz, jint buf, jboolean r, jboolean g, jboolean b, jboolean a, jlong function_pointer) {
	glColorMaskiPROC glColorMaski = (glColorMaskiPROC)((intptr_t)function_pointer);
	glColorMaski(buf, r, g, b, a);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetBooleani_1v(JNIEnv *env, jclass clazz, jint value, jint index, jlong data, jlong function_pointer) {
	GLboolean *data_address = (GLboolean *)(intptr_t)data;
	glGetBooleani_vPROC glGetBooleani_v = (glGetBooleani_vPROC)((intptr_t)function_pointer);
	glGetBooleani_v(value, index, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetIntegeri_1v(JNIEnv *env, jclass clazz, jint value, jint index, jlong data, jlong function_pointer) {
	GLint *data_address = (GLint *)(intptr_t)data;
	glGetIntegeri_vPROC glGetIntegeri_v = (glGetIntegeri_vPROC)((intptr_t)function_pointer);
	glGetIntegeri_v(value, index, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglEnablei(JNIEnv *env, jclass clazz, jint target, jint index, jlong function_pointer) {
	glEnableiPROC glEnablei = (glEnableiPROC)((intptr_t)function_pointer);
	glEnablei(target, index);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglDisablei(JNIEnv *env, jclass clazz, jint target, jint index, jlong function_pointer) {
	glDisableiPROC glDisablei = (glDisableiPROC)((intptr_t)function_pointer);
	glDisablei(target, index);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_GL30_nglIsEnabledi(JNIEnv *env, jclass clazz, jint target, jint index, jlong function_pointer) {
	glIsEnablediPROC glIsEnabledi = (glIsEnablediPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsEnabledi(target, index);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglBindBufferRange(JNIEnv *env, jclass clazz, jint target, jint index, jint buffer, jlong offset, jlong size, jlong function_pointer) {
	glBindBufferRangePROC glBindBufferRange = (glBindBufferRangePROC)((intptr_t)function_pointer);
	glBindBufferRange(target, index, buffer, offset, size);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglBindBufferBase(JNIEnv *env, jclass clazz, jint target, jint index, jint buffer, jlong function_pointer) {
	glBindBufferBasePROC glBindBufferBase = (glBindBufferBasePROC)((intptr_t)function_pointer);
	glBindBufferBase(target, index, buffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglBeginTransformFeedback(JNIEnv *env, jclass clazz, jint primitiveMode, jlong function_pointer) {
	glBeginTransformFeedbackPROC glBeginTransformFeedback = (glBeginTransformFeedbackPROC)((intptr_t)function_pointer);
	glBeginTransformFeedback(primitiveMode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglEndTransformFeedback(JNIEnv *env, jclass clazz, jlong function_pointer) {
	glEndTransformFeedbackPROC glEndTransformFeedback = (glEndTransformFeedbackPROC)((intptr_t)function_pointer);
	glEndTransformFeedback();
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglTransformFeedbackVaryings(JNIEnv *env, jclass clazz, jint program, jint count, jlong varyings, jint bufferMode, jlong function_pointer) {
	const GLchar *varyings_address = (const GLchar *)(intptr_t)varyings;
	int _str_i;
	GLchar *_str_address;
	GLchar **varyings_str = (GLchar **) malloc(count * sizeof(GLchar *));
	glTransformFeedbackVaryingsPROC glTransformFeedbackVaryings = (glTransformFeedbackVaryingsPROC)((intptr_t)function_pointer);
	_str_i = 0;
	_str_address = (GLchar *)varyings_address;
	while ( _str_i < count ) {
		varyings_str[_str_i++] = _str_address;
		_str_address += strlen(_str_address) + 1;
	}
	glTransformFeedbackVaryings(program, count, (const GLchar **)varyings_str, bufferMode);
	free(varyings_str);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGetTransformFeedbackVarying(JNIEnv *env, jclass clazz, jint program, jint index, jint bufSize, jlong length, jlong size, jlong type, jlong name, jlong function_pointer) {
	GLsizei *length_address = (GLsizei *)(intptr_t)length;
	GLsizei *size_address = (GLsizei *)(intptr_t)size;
	GLenum *type_address = (GLenum *)(intptr_t)type;
	GLchar *name_address = (GLchar *)(intptr_t)name;
	glGetTransformFeedbackVaryingPROC glGetTransformFeedbackVarying = (glGetTransformFeedbackVaryingPROC)((intptr_t)function_pointer);
	glGetTransformFeedbackVarying(program, index, bufSize, length_address, size_address, type_address, name_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglBindVertexArray(JNIEnv *env, jclass clazz, jint array, jlong function_pointer) {
	glBindVertexArrayPROC glBindVertexArray = (glBindVertexArrayPROC)((intptr_t)function_pointer);
	glBindVertexArray(array);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglDeleteVertexArrays(JNIEnv *env, jclass clazz, jint n, jlong arrays, jlong function_pointer) {
	const GLuint *arrays_address = (const GLuint *)(intptr_t)arrays;
	glDeleteVertexArraysPROC glDeleteVertexArrays = (glDeleteVertexArraysPROC)((intptr_t)function_pointer);
	glDeleteVertexArrays(n, arrays_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL30_nglGenVertexArrays(JNIEnv *env, jclass clazz, jint n, jlong arrays, jlong function_pointer) {
	GLuint *arrays_address = (GLuint *)(intptr_t)arrays;
	glGenVertexArraysPROC glGenVertexArrays = (glGenVertexArraysPROC)((intptr_t)function_pointer);
	glGenVertexArrays(n, arrays_address);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_GL30_nglIsVertexArray(JNIEnv *env, jclass clazz, jint array, jlong function_pointer) {
	glIsVertexArrayPROC glIsVertexArray = (glIsVertexArrayPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsVertexArray(array);
	return __result;
}

