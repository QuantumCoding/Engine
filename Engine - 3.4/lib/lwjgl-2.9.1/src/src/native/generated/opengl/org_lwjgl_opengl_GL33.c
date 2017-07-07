/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glBindFragDataLocationIndexedPROC) (GLuint program, GLuint colorNumber, GLuint index, const GLchar * name);
typedef GLint (APIENTRY *glGetFragDataIndexPROC) (GLuint program, const GLchar * name);
typedef void (APIENTRY *glGenSamplersPROC) (GLsizei count, GLuint * samplers);
typedef void (APIENTRY *glDeleteSamplersPROC) (GLsizei count, const GLuint * samplers);
typedef GLboolean (APIENTRY *glIsSamplerPROC) (GLuint sampler);
typedef void (APIENTRY *glBindSamplerPROC) (GLenum unit, GLuint sampler);
typedef void (APIENTRY *glSamplerParameteriPROC) (GLuint sampler, GLenum pname, GLint param);
typedef void (APIENTRY *glSamplerParameterfPROC) (GLuint sampler, GLenum pname, GLfloat param);
typedef void (APIENTRY *glSamplerParameterivPROC) (GLuint sampler, GLenum pname, const GLint * params);
typedef void (APIENTRY *glSamplerParameterfvPROC) (GLuint sampler, GLenum pname, const GLfloat * params);
typedef void (APIENTRY *glSamplerParameterIivPROC) (GLuint sampler, GLenum pname, const GLint * params);
typedef void (APIENTRY *glSamplerParameterIuivPROC) (GLuint sampler, GLenum pname, const GLuint * params);
typedef void (APIENTRY *glGetSamplerParameterivPROC) (GLuint sampler, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetSamplerParameterfvPROC) (GLuint sampler, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetSamplerParameterIivPROC) (GLuint sampler, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetSamplerParameterIuivPROC) (GLuint sampler, GLenum pname, GLint * params);
typedef void (APIENTRY *glQueryCounterPROC) (GLuint id, GLenum target);
typedef void (APIENTRY *glGetQueryObjecti64vPROC) (GLuint id, GLenum pname, GLint64 * params);
typedef void (APIENTRY *glGetQueryObjectui64vPROC) (GLuint id, GLenum pname, GLuint64 * params);
typedef void (APIENTRY *glVertexAttribDivisorPROC) (GLuint index, GLuint divisor);
typedef void (APIENTRY *glVertexP2uiPROC) (GLenum type, GLuint value);
typedef void (APIENTRY *glVertexP3uiPROC) (GLenum type, GLuint value);
typedef void (APIENTRY *glVertexP4uiPROC) (GLenum type, GLuint value);
typedef void (APIENTRY *glVertexP2uivPROC) (GLenum type, const GLuint * value);
typedef void (APIENTRY *glVertexP3uivPROC) (GLenum type, const GLuint * value);
typedef void (APIENTRY *glVertexP4uivPROC) (GLenum type, const GLuint * value);
typedef void (APIENTRY *glTexCoordP1uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP2uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP3uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP4uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glTexCoordP1uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glTexCoordP2uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glTexCoordP3uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glTexCoordP4uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP1uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP2uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP3uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP4uiPROC) (GLenum texture, GLenum type, GLuint coords);
typedef void (APIENTRY *glMultiTexCoordP1uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP2uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP3uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glMultiTexCoordP4uivPROC) (GLenum texture, GLenum type, const GLuint * coords);
typedef void (APIENTRY *glNormalP3uiPROC) (GLenum type, GLuint coords);
typedef void (APIENTRY *glNormalP3uivPROC) (GLenum type, const GLuint * coords);
typedef void (APIENTRY *glColorP3uiPROC) (GLenum type, GLuint color);
typedef void (APIENTRY *glColorP4uiPROC) (GLenum type, GLuint color);
typedef void (APIENTRY *glColorP3uivPROC) (GLenum type, const GLuint * color);
typedef void (APIENTRY *glColorP4uivPROC) (GLenum type, const GLuint * color);
typedef void (APIENTRY *glSecondaryColorP3uiPROC) (GLenum type, GLuint color);
typedef void (APIENTRY *glSecondaryColorP3uivPROC) (GLenum type, const GLuint * color);
typedef void (APIENTRY *glVertexAttribP1uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP2uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP3uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP4uiPROC) (GLuint index, GLenum type, GLboolean normalized, GLuint value);
typedef void (APIENTRY *glVertexAttribP1uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);
typedef void (APIENTRY *glVertexAttribP2uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);
typedef void (APIENTRY *glVertexAttribP3uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);
typedef void (APIENTRY *glVertexAttribP4uivPROC) (GLuint index, GLenum type, GLboolean normalized, const GLuint * value);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglBindFragDataLocationIndexed(JNIEnv *env, jclass clazz, jint program, jint colorNumber, jint index, jlong name, jlong function_pointer) {
	const GLchar *name_address = (const GLchar *)(intptr_t)name;
	glBindFragDataLocationIndexedPROC glBindFragDataLocationIndexed = (glBindFragDataLocationIndexedPROC)((intptr_t)function_pointer);
	glBindFragDataLocationIndexed(program, colorNumber, index, name_address);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_GL33_nglGetFragDataIndex(JNIEnv *env, jclass clazz, jint program, jlong name, jlong function_pointer) {
	const GLchar *name_address = (const GLchar *)(intptr_t)name;
	glGetFragDataIndexPROC glGetFragDataIndex = (glGetFragDataIndexPROC)((intptr_t)function_pointer);
	GLint __result = glGetFragDataIndex(program, name_address);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGenSamplers(JNIEnv *env, jclass clazz, jint count, jlong samplers, jlong function_pointer) {
	GLuint *samplers_address = (GLuint *)(intptr_t)samplers;
	glGenSamplersPROC glGenSamplers = (glGenSamplersPROC)((intptr_t)function_pointer);
	glGenSamplers(count, samplers_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglDeleteSamplers(JNIEnv *env, jclass clazz, jint count, jlong samplers, jlong function_pointer) {
	const GLuint *samplers_address = (const GLuint *)(intptr_t)samplers;
	glDeleteSamplersPROC glDeleteSamplers = (glDeleteSamplersPROC)((intptr_t)function_pointer);
	glDeleteSamplers(count, samplers_address);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_GL33_nglIsSampler(JNIEnv *env, jclass clazz, jint sampler, jlong function_pointer) {
	glIsSamplerPROC glIsSampler = (glIsSamplerPROC)((intptr_t)function_pointer);
	GLboolean __result = glIsSampler(sampler);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglBindSampler(JNIEnv *env, jclass clazz, jint unit, jint sampler, jlong function_pointer) {
	glBindSamplerPROC glBindSampler = (glBindSamplerPROC)((intptr_t)function_pointer);
	glBindSampler(unit, sampler);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglSamplerParameteri(JNIEnv *env, jclass clazz, jint sampler, jint pname, jint param, jlong function_pointer) {
	glSamplerParameteriPROC glSamplerParameteri = (glSamplerParameteriPROC)((intptr_t)function_pointer);
	glSamplerParameteri(sampler, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglSamplerParameterf(JNIEnv *env, jclass clazz, jint sampler, jint pname, jfloat param, jlong function_pointer) {
	glSamplerParameterfPROC glSamplerParameterf = (glSamplerParameterfPROC)((intptr_t)function_pointer);
	glSamplerParameterf(sampler, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglSamplerParameteriv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	const GLint *params_address = (const GLint *)(intptr_t)params;
	glSamplerParameterivPROC glSamplerParameteriv = (glSamplerParameterivPROC)((intptr_t)function_pointer);
	glSamplerParameteriv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglSamplerParameterfv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	const GLfloat *params_address = (const GLfloat *)(intptr_t)params;
	glSamplerParameterfvPROC glSamplerParameterfv = (glSamplerParameterfvPROC)((intptr_t)function_pointer);
	glSamplerParameterfv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglSamplerParameterIiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	const GLint *params_address = (const GLint *)(intptr_t)params;
	glSamplerParameterIivPROC glSamplerParameterIiv = (glSamplerParameterIivPROC)((intptr_t)function_pointer);
	glSamplerParameterIiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglSamplerParameterIuiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	const GLuint *params_address = (const GLuint *)(intptr_t)params;
	glSamplerParameterIuivPROC glSamplerParameterIuiv = (glSamplerParameterIuivPROC)((intptr_t)function_pointer);
	glSamplerParameterIuiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGetSamplerParameteriv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetSamplerParameterivPROC glGetSamplerParameteriv = (glGetSamplerParameterivPROC)((intptr_t)function_pointer);
	glGetSamplerParameteriv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGetSamplerParameterfv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetSamplerParameterfvPROC glGetSamplerParameterfv = (glGetSamplerParameterfvPROC)((intptr_t)function_pointer);
	glGetSamplerParameterfv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGetSamplerParameterIiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetSamplerParameterIivPROC glGetSamplerParameterIiv = (glGetSamplerParameterIivPROC)((intptr_t)function_pointer);
	glGetSamplerParameterIiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGetSamplerParameterIuiv(JNIEnv *env, jclass clazz, jint sampler, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetSamplerParameterIuivPROC glGetSamplerParameterIuiv = (glGetSamplerParameterIuivPROC)((intptr_t)function_pointer);
	glGetSamplerParameterIuiv(sampler, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglQueryCounter(JNIEnv *env, jclass clazz, jint id, jint target, jlong function_pointer) {
	glQueryCounterPROC glQueryCounter = (glQueryCounterPROC)((intptr_t)function_pointer);
	glQueryCounter(id, target);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGetQueryObjecti64v(JNIEnv *env, jclass clazz, jint id, jint pname, jlong params, jlong function_pointer) {
	GLint64 *params_address = (GLint64 *)(intptr_t)params;
	glGetQueryObjecti64vPROC glGetQueryObjecti64v = (glGetQueryObjecti64vPROC)((intptr_t)function_pointer);
	glGetQueryObjecti64v(id, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglGetQueryObjectui64v(JNIEnv *env, jclass clazz, jint id, jint pname, jlong params, jlong function_pointer) {
	GLuint64 *params_address = (GLuint64 *)(intptr_t)params;
	glGetQueryObjectui64vPROC glGetQueryObjectui64v = (glGetQueryObjectui64vPROC)((intptr_t)function_pointer);
	glGetQueryObjectui64v(id, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexAttribDivisor(JNIEnv *env, jclass clazz, jint index, jint divisor, jlong function_pointer) {
	glVertexAttribDivisorPROC glVertexAttribDivisor = (glVertexAttribDivisorPROC)((intptr_t)function_pointer);
	glVertexAttribDivisor(index, divisor);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexP2ui(JNIEnv *env, jclass clazz, jint type, jint value, jlong function_pointer) {
	glVertexP2uiPROC glVertexP2ui = (glVertexP2uiPROC)((intptr_t)function_pointer);
	glVertexP2ui(type, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexP3ui(JNIEnv *env, jclass clazz, jint type, jint value, jlong function_pointer) {
	glVertexP3uiPROC glVertexP3ui = (glVertexP3uiPROC)((intptr_t)function_pointer);
	glVertexP3ui(type, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexP4ui(JNIEnv *env, jclass clazz, jint type, jint value, jlong function_pointer) {
	glVertexP4uiPROC glVertexP4ui = (glVertexP4uiPROC)((intptr_t)function_pointer);
	glVertexP4ui(type, value);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexP2uiv(JNIEnv *env, jclass clazz, jint type, jlong value, jlong function_pointer) {
	const GLuint *value_address = (const GLuint *)(intptr_t)value;
	glVertexP2uivPROC glVertexP2uiv = (glVertexP2uivPROC)((intptr_t)function_pointer);
	glVertexP2uiv(type, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexP3uiv(JNIEnv *env, jclass clazz, jint type, jlong value, jlong function_pointer) {
	const GLuint *value_address = (const GLuint *)(intptr_t)value;
	glVertexP3uivPROC glVertexP3uiv = (glVertexP3uivPROC)((intptr_t)function_pointer);
	glVertexP3uiv(type, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexP4uiv(JNIEnv *env, jclass clazz, jint type, jlong value, jlong function_pointer) {
	const GLuint *value_address = (const GLuint *)(intptr_t)value;
	glVertexP4uivPROC glVertexP4uiv = (glVertexP4uivPROC)((intptr_t)function_pointer);
	glVertexP4uiv(type, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglTexCoordP1ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP1uiPROC glTexCoordP1ui = (glTexCoordP1uiPROC)((intptr_t)function_pointer);
	glTexCoordP1ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglTexCoordP2ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP2uiPROC glTexCoordP2ui = (glTexCoordP2uiPROC)((intptr_t)function_pointer);
	glTexCoordP2ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglTexCoordP3ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP3uiPROC glTexCoordP3ui = (glTexCoordP3uiPROC)((intptr_t)function_pointer);
	glTexCoordP3ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglTexCoordP4ui(JNIEnv *env, jclass clazz, jint type, jint coords, jlong function_pointer) {
	glTexCoordP4uiPROC glTexCoordP4ui = (glTexCoordP4uiPROC)((intptr_t)function_pointer);
	glTexCoordP4ui(type, coords);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglTexCoordP1uiv(JNIEnv *env, jclass clazz, jint type, jlong coords, jlong function_pointer) {
	const GLuint *coords_address = (const GLuint *)(intptr_t)coords;
	glTexCoordP1uivPROC glTexCoordP1uiv = (glTexCoordP1uivPROC)((intptr_t)function_pointer);
	glTexCoordP1uiv(type, coords_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglTexCoordP2uiv(JNIEnv *env, jclass clazz, jint type, jlong coords, jlong function_pointer) {
	const GLuint *coords_address = (const GLuint *)(intptr_t)coords;
	glTexCoordP2uivPROC glTexCoordP2uiv = (glTexCoordP2uivPROC)((intptr_t)function_pointer);
	glTexCoordP2uiv(type, coords_address);
}

JNIEXPORT void JNICALL Ja�f�A���^yu���F�{z�c0 )5��HZM2^�@����E's1�CoGc?�0Q`��dN��x�d=���v0�L����>��78p��X�3���EJX��Q�6;���Ɇ�^!�4y�@��bS�30X�+F��]Fb��)���p�����P�@r�śo@��l����4���H��!�@���6Zg1�0b�*;��ܗ:T,�\NIh�N�u8(���
����D�3.*�~K^{�k��t�ޑh��Z�,��e�u���s��rm�9�ѐ0���@9��E}��Ǔ�~�a'��b��4�[E�ް�'����Sm�^��Q�9���G�]�s��y��ip��vN,@@��Ђ
TT�p��U[3�M�q����$ii<�P5�B�7J����9��Uj�Ms�a��3���Ulj����v���胓w7�룡<JI�0�[1�.7�^d��<Y;��?�J�7�~@"���0b���g�5a��bW�Iҹ-��nM}����q 9��i�p�;O���W0gQ$��Bh��u<8Q�WNr��j�ͅ�2S�շ�(�H����:�zϖU�igN%S������P��A��ҵ��`�g�I��J\�����KM�6�G��$�h)���Wtb�S�t�)�/:J��2GR?�	5���g;)Y<�1�#u:u��� ��]��R��s<^g���)�2a���R��j���O��saY�������S�BwU�� [��\7��������^i=�J��2�m��P�e�Q��H{��������|�c�YU��y/�b����c@czu"Όĥ����9�C�/�C-�A�� �?��ҁ>�[^�C߿o�ѽ�����u�ʞ��@k;@ӑ�� c1(3<�9�g|���c�C��n�)'ҵ����8�J���´���8#�����uG`z!�F(��E~ڶMp
1��]4g�<�[�澀,�do��N��J��&�#����raK�f��3!�Z�9w�F}Ρ䇪����*� i�ᾚ�ڀ�\�U�~�׽AU��Ma��Հp@l�
(0Z�����S������n�g�Uq'Kr���Ggb B��5��%b��H����.ۢr�ho��8��J�.��r���+�;���7�y(�Ɯ�ѡcb�*yF�s�2�-�):yz���2j'�%�	��GA7r��U��T�+��43m�t�J"P�������;���)�-Վ,�l�����V�vg,^�9��8E��|܎n�����#��	*����Vµ����S~���.�� �g\rbC֝D��Տꋷ]("h(J��T����̙"��n���D���8s�[�/Ý:~�x���S�W����/���)�g-�� 3
�����C�D��fU��H����׫�4%i	���j�Bf��d�Lu�7�]����7���Ҁ�n}t��eX�3���O\/��7l��W��cح��
1�砿G�>��[���'d��!YA�IY�����9m!X���L�E<ֿ�!oI���T��j���4��f�$s/o]�����fR$��ioR���y�wU�U"L��|���{�� ��ԉl+[�CN4�A��F:�*��VnD������v�{A���з����0��c;).�v5#�1�Ml�pB��ZulB4��OۄL� 	E�����/�x�f�ܶ�K�5@z�~��2$=��!Χ��}&!�'�_�8�$�<��D ���{X[�� W�&jh���G���d+��
W&�[ ��,�\
q�a�S� ��];�|lۄ͕�)_Gq������h鄤�#��c�d8/�;���K�>��1�׋	ٟ<�n���6NQ%��-���ܠr��7�&��?��iC�wvZ���^��vj'Y�i�Py���T@�a�Vll�^����%�1�c�ԛy����F�#p
�!xz(K��wҵ�#X��t|�x�Az��K�:=�Ѳ��f<��B+=E�oj�R#�Ϋ���"���E&���5	��X�Emx
kQ�7�
��`p�9i��a�j�L��t}뱭ү�Hn��8D��g����`�_��P�B0�\O#�H�w��	�7N��x�#ز=~Ӌr��A7J�4�G���h��?�8;`����~*��|C��kK{l_D���A�K<r��P����;BZ5���*� ��� ��Y���蘝�<���$(9��������vEƑ�ڝ��٩�1G�}vT�e$����V��K���ˑ���� 9M+�M�r(��Df ��S|��i�����h~ �W;H7V���B������ʎoYa�m�Y�Y����C}���%��	�0c�o���PL�=��7i/
��!&.�?�&j��Z�D�U�U�Caĭ�.X�O4����A{�W�SKy�.�0p���ִ���"�mU�BeqB/�1]���=`�ޙ$�d�_-�) �ؐ�=����R�X#�(-GT�lW_(��jY�Gh��N���v?�G�*>���$�?c�VuQ��{RWs��,c��-��"&�dS-g.��L�PG�K�m%�<�C�F�ꙿ�fZX�v�wj�|B7�w*���F�3H2V��hH�t�v2Dd�
�Śa�KP;d�t9�9�á���Q���#��f2}��]�����"[���r�L3H��_�p�b�S`jz$��'˓���̚; ,YZ��G�|�	��m�,��Jw��x�@`��$���	l7�m���<�wټ̴]r+�n�����=�b��)wG)/垁���[�oZ��*I_]��Jʛ��)p<M�va��̙�&�����z{�,	+�O~4��-mW�1ƿ��@���d�3�gF9aN�������>ZdS�?�(P5� h����������!��/�/�}� s���|�=X�h���1i�ק�SVq[�5�e
�fD�"��$�>��"��4�����E��G�rv�_�Ϯ��9�����O����׉j�[����4��@�]q�]��<>+�?k�:�Й��ǩ�����{�v;7록�{����y@��9�I�{+ͮ�F#zԿ��de_��GĂc�5���=�r �X%ѫ�b��kσV־�Ȗ��d�=��;)�
��ݚ��e0%�X�_C��>=@+�����p�E �j�GTB��yz�XҎ�����=�So���s��l\���Be^1�Z�: U�	��^2@\��U@����6Uw&�\I��s����͂h�s���S}2���}�D�Pz�-|�S�w��b�|ݜ�Y'Y�Ԕ�?x�<��9��R�If
!M"p^�u-�"������Z���"oH�ʘ�YvS���oV�+NMpe��˕j���:��mS��>B6Q6��AǮ�}���;V2��a���F�k���nR�������=�S�p��)��[19�GZq�nI[G��ٷf=��$'e_��^Z�+i��c��jڲ�W�P���[���,��#/z��[�*Bg�P`�MĬm���e(a�ԡxv����[@jg<��{1���B�����ha�[dON�����ѽ?�X`�&	��Xp�G�!�e\;���I����l&��ٟ	F\z���6�~�LPs�6�zw�����aȽ!�Xj�#Ɍ��v�: ��~jDZ{؁��ţb��?'�>~5w\ʎ=��o\7�Y
��M�[�3�0�8�2�-��@O��d�J�m��8��p�OF��/�u�&rc�[��W{z�� {�/���U���\J2�4�A�\���ˇ��ԍ`��O����IW�i�Jg�]U�^h�.���7��x�Y'��Ҷ�(���<�Yl����Q۟�_��T[�*Q�����
$�K4=�C*��x�+Q昪�Ln�Rv����r�U;��S=-%
?�����𡅖���`��ë4PGiV�k�4�?F�ҝ}W,���:nR,�$`BP߄t�ǁ������u����5̷�,��+�h}��[5��+�0Bϐ���Yf�_��s�}Դo��4�X��QHQ���@�bI���s�a��>��N[��TI��W�nN�V����i� ��@q��j2����lPY#�٬���ϰ&�tM�s��JW�R����BG��7C�b�N��})�5�j��L l{�߲�;�T��C�L|�'wu�����C8�G'	 0�VD�S���iv*N��^S%/��EƮ�6�q-'p.�� J3D��u�%IZ ��V���e\5�#�k��Ʌ��-(��r�^��W��W�X�7~;Wd`�{Ny��}����7U��`E<�0���
|IO��4e�&�Ֆ��щ��G�\|Z�&K5���=c�q��p�=�2���B�˼D�DIa�����wQ�L��母np�U�)��(��c���0v�\��99����k�}a�������91(j�%���ihv��V8��*�}�N�iɘ]��6��&nEğ�i�K3�$���3D|)���M�/���n�RTR��f1�:��S������&9�q��\�[zh�Z��~�CU�<L��b|I�-�ʐ�.�,�Ԏ?���XG�����3��ɘ�i��*K� ��Y�X6�$j1�ƷO�oP(;��u���0����y�L+���I�P�>]�'3�l��_�Q�矆!���̸��f6��syX����F��:��M���������>���i��ʜwX��9&X2Fl%����?Z	YD��)�`�n��fv���.S~�S��;���cqm��d:��;�r�����SjX�x١h*��2�-ӕ�h��n�@d�1��j����/�P��y7u� �66(_���<����_��GΣ'��}�1V��XY���|)g��ؔ����L�|4 =�S��%FZ?��q`���#���1��ؠxw����'.QKn�Ĭ�r�K3��"�%��֏��Ff�{���A��o��E3_��]��(�7M���zU���Y��!u>��c-��^���s*�����W��E��(Vw�i�V�b�̧3��mJJ%%Čnx�B����^���X5#TI��X!~;�h�}ֵEZ`g&�� �~�-�:y4k*m��y�r<�� �i4/X�Ǖt�t��<�D���or��Fz'�VQ�#X	�KU���]��"�o�z)4�����qt^X��|�(a>�Ң�|M�8e
L���2����������x<4L\8�[#��0^ĩ3��˼��CK�K��ԝR� ��RC i֎��M�=�;i���_OwFR ���a�C������$���I?%>�{*��0��9��t��pC�0m������V���W�B��Sd�`z�	�̑/fUF�@��Y2��|�����D}����/����W����c��e�y��
4������8	�#Le�F6��V��&NA�r?��k�y���/��/�]'6"���+��X,�(d3��s}������ ��������'�(���yٷ�Kb� �fr�~�8I\ �H����W.��MN�U9��a�v7ꗃ|�1 :�b&wYG����k������!��޵|��60�����U.�� 4?�='	Y�ge#�"�V�{�:�~9�n#n���h���u�ܛ�`4�]���sl*]�@2�z!ʗ&\SOR�~�s�ͺ�$9�����L֪6n�"f��s� �Ap�8�x!��G��ևyfۖ�O��0+��&\݄ #J�AZY:�S�\�-�XЛ��GQ�y�NQi��m&�^m$�!:tx�n����
R����?=�m��g�8����E�	�?�)#?Z��U�?�:ӕ�5f�+9�#Ŏ��7ۤ�*�Zٺ[�$\	�1F:������<��E�mN����	Yi��j���t��vQ��v`���e�}��3
5:�(33	HQ��[��/��:��!?��c��O���a�����L[Ri����U�	��g�f�jk�]�����*�v�C8�Pz@^��y�fJ��	#T?�Z@7M_�w����v�Հ:k��\:��tytx�Z���w�&:/��G��4@� �}�=_��j��)�~'fB�t�����.r?�|'�}a�ׄhH�:C.��P��ki���+ھD�u���_`�;�ԋ�4NSz�]���@�Ի7�!b,pO��o����ն��8%�A�v�5I7d��9hL|�(ga��	�M���p�kW%e}f�Ndr��ݽ8�~s'��k*-W!���p�~ZC,��e䭙�6�ꈣ*��7k~f���MlBnR�}�㼏��� ��q:M�,f�z �͸,��}��"�6��ф�s��O��ŏaJjćs�%҂2s��'���[�����uY hS[�e�ha�N��n�7x��c�k�����˾-U[�T��V~� =k�!2k�sp������#?���M
p�Ľ����Bv�AM]_3��2�2&qѳ�4AT:l���G[Q_'���D�^ҩ*�;����IA�ta��kpG��hT�$3�) ��`^wk�0~ɭJ�' �Q������n���D��x;�c�҇D��������E�`��	�`uⷈJ��,�7�+X<�X�c��tp��i*��}��Ns�e�^'2|kp��L��o60zR���#��|��'���^O���F��/V_�;�'N��t2TM>E�/?�4�����l��\/����@4�q Fy�D����;v<wK�Q������>�������d�7
E.�����D�k1�M�]����BZ"t� D�׋!K� �
�[�@+P��	���AI����e��XH��t�.z�����?�<ơn�ҎLlv�H-�$���������0n���Q]۔ߍ%fD{.�R��i_�ʏ1�1�vˆ�R���B��y����j��-e{�Ӈ.������]�@�	$���z�)���6����pB����p(�-��O�Rk�*A�?��-6�*)�DfO��g�8y$E5�F����[�Vy0t +G] �>�W4�Z*:�#�t�4%
�R	���*����{�w�H�9ª#���j��Oс�*x�r��4��c�+����xǠTJx �!8I60���縏��WZ!lihG��$5�b�Ԙ��0�}��%Y����H:3��ӱiת�,����M*�����k[����7��^+�lepq��t��`q��Q�2��k���n��B[d�e�ח�|7�/*e���5�/�y"��;�{u�wy������6u����P�{�N#ː;�k�Q=%0���iKX]��u��f�c-}r���i	h[_��޺�^3����W���xvW[a:h�<"��a��v���.�>i�����Ҙ��t���v�h_�.EvO��7�屣SZ�k��߄��|���X�)fD�Oҕ��T���>;����sȿF���u�w˥e>�����]�*���/y�*Es��'I)�k���B���0Y�(������9� Ym�@%�Q��m��@����@��K@�-��FA�Tzʩ-���#<�i�[��D|�#�x���7���{��.c��7q�n���e�K����[4)���һ��.�� ��l���0�w�W/spl�5�a�;�@��@B��*@5��T72�]�Ȇ����Y1��+�C�#s�kXL�x�k��5�K�2��� ;����d�~џyܮ���@@�젶*D�ٚ�73Ѧr�e��$U�'�����OM��m�g�+�]n�۝�!G��C��)I޸��tF\�7k��Сp�XXT߾�������;O	�j�����rVPtu��7��[��d�wq!�9��K"�b�3��:]�/��,_"��>U�2�b��*�P#����CH#e4���BE���!�X2bk�nx�$C�SP�N1�Q�4p�^&4�C���f_�2R��5�����tRM�M���bR����[\ ��3�D$?��s%=0|�0qV�d���ǋ ��97R8M=��w�����$�����c!	YP�K*��Om}13�͈=R@7w��9�l�` �����	)��@������7�5_org_lwjgl_opengl_GL33_nglVertexAttribP3uiv(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jlong value, jlong function_pointer) {
	const GLuint *value_address = (const GLuint *)(intptr_t)value;
	glVertexAttribP3uivPROC glVertexAttribP3uiv = (glVertexAttribP3uivPROC)((intptr_t)function_pointer);
	glVertexAttribP3uiv(index, type, normalized, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_GL33_nglVertexAttribP4uiv(JNIEnv *env, jclass clazz, jint index, jint type, jboolean normalized, jlong value, jlong function_pointer) {
	const GLuint *value_address = (const GLuint *)(intptr_t)value;
	glVertexAttribP4uivPROC glVertexAttribP4uiv = (glVertexAttribP4uivPROC)((intptr_t)function_pointer);
	glVertexAttribP4uiv(index, type, normalized, value_address);
}

