/* MACHINE GENERATED FILE, DO NOT EDIT */

#include <jni.h>
#include "extgl.h"

typedef void (APIENTRY *glClientAttribDefaultEXTPROC) (GLbitfield mask);
typedef void (APIENTRY *glPushClientAttribDefaultEXTPROC) (GLbitfield mask);
typedef void (APIENTRY *glMatrixLoadfEXTPROC) (GLenum matrixMode, const GLfloat * m);
typedef void (APIENTRY *glMatrixLoaddEXTPROC) (GLenum matrixMode, const GLdouble * m);
typedef void (APIENTRY *glMatrixMultfEXTPROC) (GLenum matrixMode, const GLfloat * m);
typedef void (APIENTRY *glMatrixMultdEXTPROC) (GLenum matrixMode, const GLdouble * m);
typedef void (APIENTRY *glMatrixLoadIdentityEXTPROC) (GLenum matrixMode);
typedef void (APIENTRY *glMatrixRotatefEXTPROC) (GLenum matrixMode, GLfloat angle, GLfloat x, GLfloat y, GLfloat z);
typedef void (APIENTRY *glMatrixRotatedEXTPROC) (GLenum matrixMode, GLdouble angle, GLdouble x, GLdouble y, GLdouble z);
typedef void (APIENTRY *glMatrixScalefEXTPROC) (GLenum matrixMode, GLfloat x, GLfloat y, GLfloat z);
typedef void (APIENTRY *glMatrixScaledEXTPROC) (GLenum matrixMode, GLdouble x, GLdouble y, GLdouble z);
typedef void (APIENTRY *glMatrixTranslatefEXTPROC) (GLenum matrixMode, GLfloat x, GLfloat y, GLfloat z);
typedef void (APIENTRY *glMatrixTranslatedEXTPROC) (GLenum matrixMode, GLdouble x, GLdouble y, GLdouble z);
typedef void (APIENTRY *glMatrixOrthoEXTPROC) (GLenum matrixMode, GLdouble l, GLdouble r, GLdouble b, GLdouble t, GLdouble n, GLdouble f);
typedef void (APIENTRY *glMatrixFrustumEXTPROC) (GLenum matrixMode, GLdouble l, GLdouble r, GLdouble b, GLdouble t, GLdouble n, GLdouble f);
typedef void (APIENTRY *glMatrixPushEXTPROC) (GLenum matrixMode);
typedef void (APIENTRY *glMatrixPopEXTPROC) (GLenum matrixMode);
typedef void (APIENTRY *glTextureParameteriEXTPROC) (GLuint texture, GLenum target, GLenum pname, GLint param);
typedef void (APIENTRY *glTextureParameterivEXTPROC) (GLuint texture, GLenum target, GLenum pname, const GLint * param);
typedef void (APIENTRY *glTextureParameterfEXTPROC) (GLuint texture, GLenum target, GLenum pname, GLfloat param);
typedef void (APIENTRY *glTextureParameterfvEXTPROC) (GLuint texture, GLenum target, GLenum pname, const GLfloat * param);
typedef void (APIENTRY *glTextureImage1DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint internalformat, GLsizei width, GLint border, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glTextureImage2DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint internalformat, GLsizei width, GLsizei height, GLint border, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glTextureSubImage1DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLsizei width, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glTextureSubImage2DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLsizei width, GLsizei height, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glCopyTextureImage1DEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum internalformat, GLint x, GLint y, GLsizei width, GLint border);
typedef void (APIENTRY *glCopyTextureImage2DEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum internalformat, GLint x, GLint y, GLsizei width, GLsizei height, GLint border);
typedef void (APIENTRY *glCopyTextureSubImage1DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint x, GLint y, GLsizei width);
typedef void (APIENTRY *glCopyTextureSubImage2DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint x, GLint y, GLsizei width, GLsizei height);
typedef void (APIENTRY *glGetTextureImageEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum format, GLenum type, GLvoid * pixels);
typedef void (APIENTRY *glGetTextureParameterfvEXTPROC) (GLuint texture, GLenum target, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetTextureParameterivEXTPROC) (GLuint texture, GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetTextureLevelParameterfvEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetTextureLevelParameterivEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum pname, GLint * params);
typedef void (APIENTRY *glTextureImage3DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint internalformat, GLsizei width, GLsizei height, GLsizei depth, GLint border, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glTextureSubImage3DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLsizei width, GLsizei height, GLsizei depth, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glCopyTextureSubImage3DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLint x, GLint y, GLsizei width, GLsizei height);
typedef void (APIENTRY *glBindMultiTextureEXTPROC) (GLenum texunit, GLenum target, GLuint texture);
typedef void (APIENTRY *glMultiTexCoordPointerEXTPROC) (GLenum texunit, GLint size, GLenum type, GLsizei stride, const GLvoid * pointer);
typedef void (APIENTRY *glMultiTexEnvfEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLfloat param);
typedef void (APIENTRY *glMultiTexEnvfvEXTPROC) (GLenum texunit, GLenum target, GLenum pname, const GLfloat * params);
typedef void (APIENTRY *glMultiTexEnviEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLint param);
typedef void (APIENTRY *glMultiTexEnvivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, const GLint * params);
typedef void (APIENTRY *glMultiTexGendEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, GLdouble param);
typedef void (APIENTRY *glMultiTexGendvEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, const GLdouble * params);
typedef void (APIENTRY *glMultiTexGenfEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, GLfloat param);
typedef void (APIENTRY *glMultiTexGenfvEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, const GLfloat * params);
typedef void (APIENTRY *glMultiTexGeniEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, GLint param);
typedef void (APIENTRY *glMultiTexGenivEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, const GLint * params);
typedef void (APIENTRY *glGetMultiTexEnvfvEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetMultiTexEnvivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetMultiTexGendvEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, GLdouble * params);
typedef void (APIENTRY *glGetMultiTexGenfvEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetMultiTexGenivEXTPROC) (GLenum texunit, GLenum coord, GLenum pname, GLint * params);
typedef void (APIENTRY *glMultiTexParameteriEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLint param);
typedef void (APIENTRY *glMultiTexParameterivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, const GLint * param);
typedef void (APIENTRY *glMultiTexParameterfEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLfloat param);
typedef void (APIENTRY *glMultiTexParameterfvEXTPROC) (GLenum texunit, GLenum target, GLenum pname, const GLfloat * param);
typedef void (APIENTRY *glMultiTexImage1DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint internalformat, GLsizei width, GLint border, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glMultiTexImage2DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint internalformat, GLsizei width, GLsizei height, GLint border, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glMultiTexSubImage1DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLsizei width, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glMultiTexSubImage2DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLsizei width, GLsizei height, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glCopyMultiTexImage1DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum internalformat, GLint x, GLint y, GLsizei width, GLint border);
typedef void (APIENTRY *glCopyMultiTexImage2DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum internalformat, GLint x, GLint y, GLsizei width, GLsizei height, GLint border);
typedef void (APIENTRY *glCopyMultiTexSubImage1DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint x, GLint y, GLsizei width);
typedef void (APIENTRY *glCopyMultiTexSubImage2DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint x, GLint y, GLsizei width, GLsizei height);
typedef void (APIENTRY *glGetMultiTexImageEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum format, GLenum type, GLvoid * pixels);
typedef void (APIENTRY *glGetMultiTexParameterfvEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetMultiTexParameterivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetMultiTexLevelParameterfvEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum pname, GLfloat * params);
typedef void (APIENTRY *glGetMultiTexLevelParameterivEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum pname, GLint * params);
typedef void (APIENTRY *glMultiTexImage3DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint internalformat, GLsizei width, GLsizei height, GLsizei depth, GLint border, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glMultiTexSubImage3DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLsizei width, GLsizei height, GLsizei depth, GLenum format, GLenum type, const GLvoid * pixels);
typedef void (APIENTRY *glCopyMultiTexSubImage3DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLint x, GLint y, GLsizei width, GLsizei height);
typedef void (APIENTRY *glEnableClientStateIndexedEXTPROC) (GLenum array, GLuint index);
typedef void (APIENTRY *glDisableClientStateIndexedEXTPROC) (GLenum array, GLuint index);
typedef void (APIENTRY *glEnableClientStateiEXTPROC) (GLenum array, GLuint index);
typedef void (APIENTRY *glDisableClientStateiEXTPROC) (GLenum array, GLuint index);
typedef void (APIENTRY *glGetFloatIndexedvEXTPROC) (GLenum pname, GLuint index, GLfloat * params);
typedef void (APIENTRY *glGetDoubleIndexedvEXTPROC) (GLenum pname, GLuint index, GLdouble * params);
typedef void (APIENTRY *glGetPointerIndexedvEXTPROC) (GLenum pname, GLuint index, GLvoid ** params);
typedef void (APIENTRY *glGetFloati_vEXTPROC) (GLenum pname, GLuint index, GLfloat * params);
typedef void (APIENTRY *glGetDoublei_vEXTPROC) (GLenum pname, GLuint index, GLdouble * params);
typedef void (APIENTRY *glGetPointeri_vEXTPROC) (GLenum pname, GLuint index, GLvoid ** params);
typedef void (APIENTRY *glNamedProgramStringEXTPROC) (GLuint program, GLenum target, GLenum format, GLsizei len, const GLvoid * string);
typedef void (APIENTRY *glNamedProgramLocalParameter4dEXTPROC) (GLuint program, GLenum target, GLuint index, GLdouble x, GLdouble y, GLdouble z, GLdouble w);
typedef void (APIENTRY *glNamedProgramLocalParameter4dvEXTPROC) (GLuint program, GLenum target, GLuint index, const GLdouble * params);
typedef void (APIENTRY *glNamedProgramLocalParameter4fEXTPROC) (GLuint program, GLenum target, GLuint index, GLfloat x, GLfloat y, GLfloat z, GLfloat w);
typedef void (APIENTRY *glNamedProgramLocalParameter4fvEXTPROC) (GLuint program, GLenum target, GLuint index, const GLfloat * params);
typedef void (APIENTRY *glGetNamedProgramLocalParameterdvEXTPROC) (GLuint program, GLenum target, GLuint index, GLdouble * params);
typedef void (APIENTRY *glGetNamedProgramLocalParameterfvEXTPROC) (GLuint program, GLenum target, GLuint index, GLfloat * params);
typedef void (APIENTRY *glGetNamedProgramivEXTPROC) (GLuint program, GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetNamedProgramStringEXTPROC) (GLuint program, GLenum target, GLenum pname, GLvoid * string);
typedef void (APIENTRY *glCompressedTextureImage3DEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum internalformat, GLsizei width, GLsizei height, GLsizei depth, GLint border, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedTextureImage2DEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum internalformat, GLsizei width, GLsizei height, GLint border, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedTextureImage1DEXTPROC) (GLuint texture, GLenum target, GLint level, GLenum internalformat, GLsizei width, GLint border, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedTextureSubImage3DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLsizei width, GLsizei height, GLsizei depth, GLenum format, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedTextureSubImage2DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLsizei width, GLsizei height, GLenum format, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedTextureSubImage1DEXTPROC) (GLuint texture, GLenum target, GLint level, GLint xoffset, GLsizei width, GLenum format, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glGetCompressedTextureImageEXTPROC) (GLuint texture, GLenum target, GLint level, GLvoid * img);
typedef void (APIENTRY *glCompressedMultiTexImage3DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum internalformat, GLsizei width, GLsizei height, GLsizei depth, GLint border, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedMultiTexImage2DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum internalformat, GLsizei width, GLsizei height, GLint border, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedMultiTexImage1DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLenum internalformat, GLsizei width, GLint border, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedMultiTexSubImage3DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLint zoffset, GLsizei width, GLsizei height, GLsizei depth, GLenum format, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedMultiTexSubImage2DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLint yoffset, GLsizei width, GLsizei height, GLenum format, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glCompressedMultiTexSubImage1DEXTPROC) (GLenum texunit, GLenum target, GLint level, GLint xoffset, GLsizei width, GLenum format, GLsizei imageSize, const GLvoid * data);
typedef void (APIENTRY *glGetCompressedMultiTexImageEXTPROC) (GLenum texunit, GLenum target, GLint level, GLvoid * img);
typedef void (APIENTRY *glMatrixLoadTransposefEXTPROC) (GLenum matrixMode, const GLfloat * m);
typedef void (APIENTRY *glMatrixLoadTransposedEXTPROC) (GLenum matrixMode, const GLdouble * m);
typedef void (APIENTRY *glMatrixMultTransposefEXTPROC) (GLenum matrixMode, const GLfloat * m);
typedef void (APIENTRY *glMatrixMultTransposedEXTPROC) (GLenum matrixMode, const GLdouble * m);
typedef void (APIENTRY *glNamedBufferDataEXTPROC) (GLuint buffer, GLsizeiptr size, const GLvoid * data, GLenum usage);
typedef void (APIENTRY *glNamedBufferSubDataEXTPROC) (GLuint buffer, GLintptr offset, GLsizeiptr size, const GLvoid * data);
typedef GLvoid * (APIENTRY *glMapNamedBufferEXTPROC) (GLuint buffer, GLenum access);
typedef GLboolean (APIENTRY *glUnmapNamedBufferEXTPROC) (GLuint buffer);
typedef void (APIENTRY *glGetNamedBufferParameterivEXTPROC) (GLuint buffer, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetNamedBufferPointervEXTPROC) (GLuint buffer, GLenum pname, GLvoid ** params);
typedef void (APIENTRY *glGetNamedBufferSubDataEXTPROC) (GLuint buffer, GLintptr offset, GLsizeiptr size, GLvoid * data);
typedef void (APIENTRY *glProgramUniform1fEXTPROC) (GLuint program, GLint location, GLfloat v0);
typedef void (APIENTRY *glProgramUniform2fEXTPROC) (GLuint program, GLint location, GLfloat v0, GLfloat v1);
typedef void (APIENTRY *glProgramUniform3fEXTPROC) (GLuint program, GLint location, GLfloat v0, GLfloat v1, GLfloat v2);
typedef void (APIENTRY *glProgramUniform4fEXTPROC) (GLuint program, GLint location, GLfloat v0, GLfloat v1, GLfloat v2, GLfloat v3);
typedef void (APIENTRY *glProgramUniform1iEXTPROC) (GLuint program, GLint location, GLint v0);
typedef void (APIENTRY *glProgramUniform2iEXTPROC) (GLuint program, GLint location, GLint v0, GLint v1);
typedef void (APIENTRY *glProgramUniform3iEXTPROC) (GLuint program, GLint location, GLint v0, GLint v1, GLint v2);
typedef void (APIENTRY *glProgramUniform4iEXTPROC) (GLuint program, GLint location, GLint v0, GLint v1, GLint v2, GLint v3);
typedef void (APIENTRY *glProgramUniform1fvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLfloat * value);
typedef void (APIENTRY *glProgramUniform2fvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLfloat * value);
typedef void (APIENTRY *glProgramUniform3fvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLfloat * value);
typedef void (APIENTRY *glProgramUniform4fvEXTPROC) (GLuint program, GLint location, GLsizei count, const GLfloat * value);
typedef void (APIENTRY *glProgramUniform1ivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLint * value);
typedef void (APIENTRY *glProgramUniform2ivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLint * value);
typedef void (APIENTRY *glProgramUniform3ivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLint * value);
typedef void (APIENTRY *glProgramUniform4ivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLint * value);
typedef void (APIENTRY *glProgramUniformMatrix2fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix3fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix4fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix2x3fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix3x2fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix2x4fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix4x2fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix3x4fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glProgramUniformMatrix4x3fvEXTPROC) (GLuint program, GLint location, GLsizei count, GLboolean transpose, const GLfloat * value);
typedef void (APIENTRY *glTextureBufferEXTPROC) (GLuint texture, GLenum target, GLenum internalformat, GLuint buffer);
typedef void (APIENTRY *glMultiTexBufferEXTPROC) (GLenum texunit, GLenum target, GLenum internalformat, GLuint buffer);
typedef void (APIENTRY *glTextureParameterIivEXTPROC) (GLuint texture, GLenum target, GLenum pname, const GLint * params);
typedef void (APIENTRY *glTextureParameterIuivEXTPROC) (GLuint texture, GLenum target, GLenum pname, const GLuint * params);
typedef void (APIENTRY *glGetTextureParameterIivEXTPROC) (GLuint texture, GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetTextureParameterIuivEXTPROC) (GLuint texture, GLenum target, GLenum pname, GLuint * params);
typedef void (APIENTRY *glMultiTexParameterIivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, const GLint * params);
typedef void (APIENTRY *glMultiTexParameterIuivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, const GLuint * params);
typedef void (APIENTRY *glGetMultiTexParameterIivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLint * params);
typedef void (APIENTRY *glGetMultiTexParameterIuivEXTPROC) (GLenum texunit, GLenum target, GLenum pname, GLuint * params);
typedef void (APIENTRY *glProgramUniform1uiEXTPROC) (GLuint program, GLint location, GLuint v0);
typedef void (APIENTRY *glProgramUniform2uiEXTPROC) (GLuint program, GLint location, GLuint v0, GLuint v1);
typedef void (APIENTRY *glProgramUniform3uiEXTPROC) (GLuint program, GLint location, GLuint v0, GLuint v1, GLuint v2);
typedef void (APIENTRY *glProgramUniform4uiEXTPROC) (GLuint program, GLint location, GLuint v0, GLuint v1, GLuint v2, GLuint v3);
typedef void (APIENTRY *glProgramUniform1uivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glProgramUniform2uivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glProgramUniform3uivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glProgramUniform4uivEXTPROC) (GLuint program, GLint location, GLsizei count, const GLuint * value);
typedef void (APIENTRY *glNamedProgramLocalParameters4fvEXTPROC) (GLuint program, GLenum target, GLuint index, GLsizei count, const GLfloat * params);
typedef void (APIENTRY *glNamedProgramLocalParameterI4iEXTPROC) (GLuint program, GLenum target, GLuint index, GLint x, GLint y, GLint z, GLint w);
typedef void (APIENTRY *glNamedProgramLocalParameterI4ivEXTPROC) (GLuint program, GLenum target, GLuint index, const GLint * params);
typedef void (APIENTRY *glNamedProgramLocalParametersI4ivEXTPROC) (GLuint program, GLenum target, GLuint index, GLsizei count, const GLint * params);
typedef void (APIENTRY *glNamedProgramLocalParameterI4uiEXTPROC) (GLuint program, GLenum target, GLuint index, GLuint x, GLuint y, GLuint z, GLuint w);
typedef void (APIENTRY *glNamedProgramLocalParameterI4uivEXTPROC) (GLuint program, GLenum target, GLuint index, const GLuint * params);
typedef void (APIENTRY *glNamedProgramLocalParametersI4uivEXTPROC) (GLuint program, GLenum target, GLuint index, GLsizei count, const GLuint * params);
typedef void (APIENTRY *glGetNamedProgramLocalParameterIivEXTPROC) (GLuint program, GLenum target, GLuint index, GLint * params);
typedef void (APIENTRY *glGetNamedProgramLocalParameterIuivEXTPROC) (GLuint program, GLenum target, GLuint index, GLuint * params);
typedef void (APIENTRY *glNamedRenderbufferStorageEXTPROC) (GLuint renderbuffer, GLenum internalformat, GLsizei width, GLsizei height);
typedef void (APIENTRY *glGetNamedRenderbufferParameterivEXTPROC) (GLuint renderbuffer, GLenum pname, GLint * params);
typedef void (APIENTRY *glNamedRenderbufferStorageMultisampleEXTPROC) (GLuint renderbuffer, GLsizei samples, GLenum internalformat, GLsizei width, GLsizei height);
typedef void (APIENTRY *glNamedRenderbufferStorageMultisampleCoverageEXTPROC) (GLuint renderbuffer, GLsizei coverageSamples, GLsizei colorSamples, GLenum internalformat, GLsizei width, GLsizei height);
typedef GLenum (APIENTRY *glCheckNamedFramebufferStatusEXTPROC) (GLuint framebuffer, GLenum target);
typedef void (APIENTRY *glNamedFramebufferTexture1DEXTPROC) (GLuint framebuffer, GLenum attachment, GLenum textarget, GLuint texture, GLint level);
typedef void (APIENTRY *glNamedFramebufferTexture2DEXTPROC) (GLuint framebuffer, GLenum attachment, GLenum textarget, GLuint texture, GLint level);
typedef void (APIENTRY *glNamedFramebufferTexture3DEXTPROC) (GLuint framebuffer, GLenum attachment, GLenum textarget, GLuint texture, GLint level, GLint zoffset);
typedef void (APIENTRY *glNamedFramebufferRenderbufferEXTPROC) (GLuint framebuffer, GLenum attachment, GLenum renderbuffertarget, GLuint renderbuffer);
typedef void (APIENTRY *glGetNamedFramebufferAttachmentParameterivEXTPROC) (GLuint framebuffer, GLenum attachment, GLenum pname, GLint * params);
typedef void (APIENTRY *glGenerateTextureMipmapEXTPROC) (GLuint texture, GLenum target);
typedef void (APIENTRY *glGenerateMultiTexMipmapEXTPROC) (GLenum texunit, GLenum target);
typedef void (APIENTRY *glFramebufferDrawBufferEXTPROC) (GLuint framebuffer, GLenum mode);
typedef void (APIENTRY *glFramebufferDrawBuffersEXTPROC) (GLuint framebuffer, GLsizei n, const GLenum * bufs);
typedef void (APIENTRY *glFramebufferReadBufferEXTPROC) (GLuint framebuffer, GLenum mode);
typedef void (APIENTRY *glGetFramebufferParameterivEXTPROC) (GLuint framebuffer, GLenum pname, GLint * param);
typedef void (APIENTRY *glNamedCopyBufferSubDataEXTPROC) (GLuint readBuffer, GLuint writeBuffer, GLintptr readoffset, GLintptr writeoffset, GLsizeiptr size);
typedef void (APIENTRY *glNamedFramebufferTextureEXTPROC) (GLuint framebuffer, GLenum attachment, GLuint texture, GLint level);
typedef void (APIENTRY *glNamedFramebufferTextureLayerEXTPROC) (GLuint framebuffer, GLenum attachment, GLuint texture, GLint level, GLint layer);
typedef void (APIENTRY *glNamedFramebufferTextureFaceEXTPROC) (GLuint framebuffer, GLenum attachment, GLuint texture, GLint level, GLenum face);
typedef void (APIENTRY *glTextureRenderbufferEXTPROC) (GLuint texture, GLenum target, GLuint renderbuffer);
typedef void (APIENTRY *glMultiTexRenderbufferEXTPROC) (GLenum texunit, GLenum target, GLuint renderbuffer);
typedef void (APIENTRY *glVertexArrayVertexOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLint size, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayColorOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLint size, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayEdgeFlagOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayIndexOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayNormalOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayTexCoordOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLint size, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayMultiTexCoordOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLenum texunit, GLint size, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayFogCoordOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArraySecondaryColorOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLint size, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayVertexAttribOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLuint index, GLint size, GLenum type, GLboolean normalized, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glVertexArrayVertexAttribIOffsetEXTPROC) (GLuint vaobj, GLuint buffer, GLuint index, GLint size, GLenum type, GLsizei stride, GLintptr offset);
typedef void (APIENTRY *glEnableVertexArrayEXTPROC) (GLuint vaobj, GLenum array);
typedef void (APIENTRY *glDisableVertexArrayEXTPROC) (GLuint vaobj, GLenum array);
typedef void (APIENTRY *glEnableVertexArrayAttribEXTPROC) (GLuint vaobj, GLuint index);
typedef void (APIENTRY *glDisableVertexArrayAttribEXTPROC) (GLuint vaobj, GLuint index);
typedef void (APIENTRY *glGetVertexArrayIntegervEXTPROC) (GLuint vaobj, GLenum pname, GLint * param);
typedef void (APIENTRY *glGetVertexArrayPointervEXTPROC) (GLuint vaobj, GLenum pname, GLvoid ** param);
typedef void (APIENTRY *glGetVertexArrayIntegeri_vEXTPROC) (GLuint vaobj, GLuint index, GLenum pname, GLint * param);
typedef void (APIENTRY *glGetVertexArrayPointeri_vEXTPROC) (GLuint vaobj, GLuint index, GLenum pname, GLvoid ** param);
typedef GLvoid * (APIENTRY *glMapNamedBufferRangeEXTPROC) (GLuint buffer, GLintptr offset, GLsizeiptr length, GLbitfield access);
typedef void (APIENTRY *glFlushMappedNamedBufferRangeEXTPROC) (GLuint buffer, GLintptr offset, GLsizeiptr length);

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglClientAttribDefaultEXT(JNIEnv *env, jclass clazz, jint mask, jlong function_pointer) {
	glClientAttribDefaultEXTPROC glClientAttribDefaultEXT = (glClientAttribDefaultEXTPROC)((intptr_t)function_pointer);
	glClientAttribDefaultEXT(mask);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglPushClientAttribDefaultEXT(JNIEnv *env, jclass clazz, jint mask, jlong function_pointer) {
	glPushClientAttribDefaultEXTPROC glPushClientAttribDefaultEXT = (glPushClientAttribDefaultEXTPROC)((intptr_t)function_pointer);
	glPushClientAttribDefaultEXT(mask);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixLoadfEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLfloat *m_address = (const GLfloat *)(intptr_t)m;
	glMatrixLoadfEXTPROC glMatrixLoadfEXT = (glMatrixLoadfEXTPROC)((intptr_t)function_pointer);
	glMatrixLoadfEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixLoaddEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLdouble *m_address = (const GLdouble *)(intptr_t)m;
	glMatrixLoaddEXTPROC glMatrixLoaddEXT = (glMatrixLoaddEXTPROC)((intptr_t)function_pointer);
	glMatrixLoaddEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixMultfEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLfloat *m_address = (const GLfloat *)(intptr_t)m;
	glMatrixMultfEXTPROC glMatrixMultfEXT = (glMatrixMultfEXTPROC)((intptr_t)function_pointer);
	glMatrixMultfEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixMultdEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLdouble *m_address = (const GLdouble *)(intptr_t)m;
	glMatrixMultdEXTPROC glMatrixMultdEXT = (glMatrixMultdEXTPROC)((intptr_t)function_pointer);
	glMatrixMultdEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixLoadIdentityEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong function_pointer) {
	glMatrixLoadIdentityEXTPROC glMatrixLoadIdentityEXT = (glMatrixLoadIdentityEXTPROC)((intptr_t)function_pointer);
	glMatrixLoadIdentityEXT(matrixMode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixRotatefEXT(JNIEnv *env, jclass clazz, jint matrixMode, jfloat angle, jfloat x, jfloat y, jfloat z, jlong function_pointer) {
	glMatrixRotatefEXTPROC glMatrixRotatefEXT = (glMatrixRotatefEXTPROC)((intptr_t)function_pointer);
	glMatrixRotatefEXT(matrixMode, angle, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixRotatedEXT(JNIEnv *env, jclass clazz, jint matrixMode, jdouble angle, jdouble x, jdouble y, jdouble z, jlong function_pointer) {
	glMatrixRotatedEXTPROC glMatrixRotatedEXT = (glMatrixRotatedEXTPROC)((intptr_t)function_pointer);
	glMatrixRotatedEXT(matrixMode, angle, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixScalefEXT(JNIEnv *env, jclass clazz, jint matrixMode, jfloat x, jfloat y, jfloat z, jlong function_pointer) {
	glMatrixScalefEXTPROC glMatrixScalefEXT = (glMatrixScalefEXTPROC)((intptr_t)function_pointer);
	glMatrixScalefEXT(matrixMode, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixScaledEXT(JNIEnv *env, jclass clazz, jint matrixMode, jdouble x, jdouble y, jdouble z, jlong function_pointer) {
	glMatrixScaledEXTPROC glMatrixScaledEXT = (glMatrixScaledEXTPROC)((intptr_t)function_pointer);
	glMatrixScaledEXT(matrixMode, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixTranslatefEXT(JNIEnv *env, jclass clazz, jint matrixMode, jfloat x, jfloat y, jfloat z, jlong function_pointer) {
	glMatrixTranslatefEXTPROC glMatrixTranslatefEXT = (glMatrixTranslatefEXTPROC)((intptr_t)function_pointer);
	glMatrixTranslatefEXT(matrixMode, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixTranslatedEXT(JNIEnv *env, jclass clazz, jint matrixMode, jdouble x, jdouble y, jdouble z, jlong function_pointer) {
	glMatrixTranslatedEXTPROC glMatrixTranslatedEXT = (glMatrixTranslatedEXTPROC)((intptr_t)function_pointer);
	glMatrixTranslatedEXT(matrixMode, x, y, z);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixOrthoEXT(JNIEnv *env, jclass clazz, jint matrixMode, jdouble l, jdouble r, jdouble b, jdouble t, jdouble n, jdouble f, jlong function_pointer) {
	glMatrixOrthoEXTPROC glMatrixOrthoEXT = (glMatrixOrthoEXTPROC)((intptr_t)function_pointer);
	glMatrixOrthoEXT(matrixMode, l, r, b, t, n, f);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixFrustumEXT(JNIEnv *env, jclass clazz, jint matrixMode, jdouble l, jdouble r, jdouble b, jdouble t, jdouble n, jdouble f, jlong function_pointer) {
	glMatrixFrustumEXTPROC glMatrixFrustumEXT = (glMatrixFrustumEXTPROC)((intptr_t)function_pointer);
	glMatrixFrustumEXT(matrixMode, l, r, b, t, n, f);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixPushEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong function_pointer) {
	glMatrixPushEXTPROC glMatrixPushEXT = (glMatrixPushEXTPROC)((intptr_t)function_pointer);
	glMatrixPushEXT(matrixMode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixPopEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong function_pointer) {
	glMatrixPopEXTPROC glMatrixPopEXT = (glMatrixPopEXTPROC)((intptr_t)function_pointer);
	glMatrixPopEXT(matrixMode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureParameteriEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jint param, jlong function_pointer) {
	glTextureParameteriEXTPROC glTextureParameteriEXT = (glTextureParameteriEXTPROC)((intptr_t)function_pointer);
	glTextureParameteriEXT(texture, target, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureParameterivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong param, jlong function_pointer) {
	const GLint *param_address = (const GLint *)(intptr_t)param;
	glTextureParameterivEXTPROC glTextureParameterivEXT = (glTextureParameterivEXTPROC)((intptr_t)function_pointer);
	glTextureParameterivEXT(texture, target, pname, param_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureParameterfEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jfloat param, jlong function_pointer) {
	glTextureParameterfEXTPROC glTextureParameterfEXT = (glTextureParameterfEXTPROC)((intptr_t)function_pointer);
	glTextureParameterfEXT(texture, target, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureParameterfvEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong param, jlong function_pointer) {
	const GLfloat *param_address = (const GLfloat *)(intptr_t)param;
	glTextureParameterfvEXTPROC glTextureParameterfvEXT = (glTextureParameterfvEXTPROC)((intptr_t)function_pointer);
	glTextureParameterfvEXT(texture, target, pname, param_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureImage1DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint border, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glTextureImage1DEXTPROC glTextureImage1DEXT = (glTextureImage1DEXTPROC)((intptr_t)function_pointer);
	glTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureImage1DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint border, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glTextureImage1DEXTPROC glTextureImage1DEXT = (glTextureImage1DEXTPROC)((intptr_t)function_pointer);
	glTextureImage1DEXT(texture, target, level, internalformat, width, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureImage2DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glTextureImage2DEXTPROC glTextureImage2DEXT = (glTextureImage2DEXTPROC)((intptr_t)function_pointer);
	glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureImage2DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glTextureImage2DEXTPROC glTextureImage2DEXT = (glTextureImage2DEXTPROC)((intptr_t)function_pointer);
	glTextureImage2DEXT(texture, target, level, internalformat, width, height, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureSubImage1DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint width, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glTextureSubImage1DEXTPROC glTextureSubImage1DEXT = (glTextureSubImage1DEXTPROC)((intptr_t)function_pointer);
	glTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureSubImage1DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint width, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glTextureSubImage1DEXTPROC glTextureSubImage1DEXT = (glTextureSubImage1DEXTPROC)((intptr_t)function_pointer);
	glTextureSubImage1DEXT(texture, target, level, xoffset, width, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureSubImage2DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glTextureSubImage2DEXTPROC glTextureSubImage2DEXT = (glTextureSubImage2DEXTPROC)((intptr_t)function_pointer);
	glTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureSubImage2DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glTextureSubImage2DEXTPROC glTextureSubImage2DEXT = (glTextureSubImage2DEXTPROC)((intptr_t)function_pointer);
	glTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, width, height, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyTextureImage1DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint x, jint y, jint width, jint border, jlong function_pointer) {
	glCopyTextureImage1DEXTPROC glCopyTextureImage1DEXT = (glCopyTextureImage1DEXTPROC)((intptr_t)function_pointer);
	glCopyTextureImage1DEXT(texture, target, level, internalformat, x, y, width, border);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyTextureImage2DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint x, jint y, jint width, jint height, jint border, jlong function_pointer) {
	glCopyTextureImage2DEXTPROC glCopyTextureImage2DEXT = (glCopyTextureImage2DEXTPROC)((intptr_t)function_pointer);
	glCopyTextureImage2DEXT(texture, target, level, internalformat, x, y, width, height, border);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyTextureSubImage1DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint x, jint y, jint width, jlong function_pointer) {
	glCopyTextureSubImage1DEXTPROC glCopyTextureSubImage1DEXT = (glCopyTextureSubImage1DEXTPROC)((intptr_t)function_pointer);
	glCopyTextureSubImage1DEXT(texture, target, level, xoffset, x, y, width);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyTextureSubImage2DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint yoffset, jint x, jint y, jint width, jint height, jlong function_pointer) {
	glCopyTextureSubImage2DEXTPROC glCopyTextureSubImage2DEXT = (glCopyTextureSubImage2DEXTPROC)((intptr_t)function_pointer);
	glCopyTextureSubImage2DEXT(texture, target, level, xoffset, yoffset, x, y, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureImageEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint format, jint type, jlong pixels, jlong function_pointer) {
	GLvoid *pixels_address = (GLvoid *)(intptr_t)pixels;
	glGetTextureImageEXTPROC glGetTextureImageEXT = (glGetTextureImageEXTPROC)((intptr_t)function_pointer);
	glGetTextureImageEXT(texture, target, level, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureImageEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	GLvoid *pixels_address = (GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glGetTextureImageEXTPROC glGetTextureImageEXT = (glGetTextureImageEXTPROC)((intptr_t)function_pointer);
	glGetTextureImageEXT(texture, target, level, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureParameterfvEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetTextureParameterfvEXTPROC glGetTextureParameterfvEXT = (glGetTextureParameterfvEXTPROC)((intptr_t)function_pointer);
	glGetTextureParameterfvEXT(texture, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureParameterivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetTextureParameterivEXTPROC glGetTextureParameterivEXT = (glGetTextureParameterivEXTPROC)((intptr_t)function_pointer);
	glGetTextureParameterivEXT(texture, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureLevelParameterfvEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint pname, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetTextureLevelParameterfvEXTPROC glGetTextureLevelParameterfvEXT = (glGetTextureLevelParameterfvEXTPROC)((intptr_t)function_pointer);
	glGetTextureLevelParameterfvEXT(texture, target, level, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureLevelParameterivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetTextureLevelParameterivEXTPROC glGetTextureLevelParameterivEXT = (glGetTextureLevelParameterivEXTPROC)((intptr_t)function_pointer);
	glGetTextureLevelParameterivEXT(texture, target, level, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureImage3DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint depth, jint border, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glTextureImage3DEXTPROC glTextureImage3DEXT = (glTextureImage3DEXTPROC)((intptr_t)function_pointer);
	glTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureImage3DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint depth, jint border, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glTextureImage3DEXTPROC glTextureImage3DEXT = (glTextureImage3DEXTPROC)((intptr_t)function_pointer);
	glTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureSubImage3DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint width, jint height, jint depth, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glTextureSubImage3DEXTPROC glTextureSubImage3DEXT = (glTextureSubImage3DEXTPROC)((intptr_t)function_pointer);
	glTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureSubImage3DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint width, jint height, jint depth, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glTextureSubImage3DEXTPROC glTextureSubImage3DEXT = (glTextureSubImage3DEXTPROC)((intptr_t)function_pointer);
	glTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, width, height, depth, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyTextureSubImage3DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint x, jint y, jint width, jint height, jlong function_pointer) {
	glCopyTextureSubImage3DEXTPROC glCopyTextureSubImage3DEXT = (glCopyTextureSubImage3DEXTPROC)((intptr_t)function_pointer);
	glCopyTextureSubImage3DEXT(texture, target, level, xoffset, yoffset, zoffset, x, y, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglBindMultiTextureEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint texture, jlong function_pointer) {
	glBindMultiTextureEXTPROC glBindMultiTextureEXT = (glBindMultiTextureEXTPROC)((intptr_t)function_pointer);
	glBindMultiTextureEXT(texunit, target, texture);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexCoordPointerEXT(JNIEnv *env, jclass clazz, jint texunit, jint size, jint type, jint stride, jlong pointer, jlong function_pointer) {
	const GLvoid *pointer_address = (const GLvoid *)(intptr_t)pointer;
	glMultiTexCoordPointerEXTPROC glMultiTexCoordPointerEXT = (glMultiTexCoordPointerEXTPROC)((intptr_t)function_pointer);
	glMultiTexCoordPointerEXT(texunit, size, type, stride, pointer_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexCoordPointerEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint size, jint type, jint stride, jlong pointer_buffer_offset, jlong function_pointer) {
	const GLvoid *pointer_address = (const GLvoid *)(intptr_t)offsetToPointer(pointer_buffer_offset);
	glMultiTexCoordPointerEXTPROC glMultiTexCoordPointerEXT = (glMultiTexCoordPointerEXTPROC)((intptr_t)function_pointer);
	glMultiTexCoordPointerEXT(texunit, size, type, stride, pointer_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexEnvfEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jfloat param, jlong function_pointer) {
	glMultiTexEnvfEXTPROC glMultiTexEnvfEXT = (glMultiTexEnvfEXTPROC)((intptr_t)function_pointer);
	glMultiTexEnvfEXT(texunit, target, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexEnvfvEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	const GLfloat *params_address = (const GLfloat *)(intptr_t)params;
	glMultiTexEnvfvEXTPROC glMultiTexEnvfvEXT = (glMultiTexEnvfvEXTPROC)((intptr_t)function_pointer);
	glMultiTexEnvfvEXT(texunit, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexEnviEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jint param, jlong function_pointer) {
	glMultiTexEnviEXTPROC glMultiTexEnviEXT = (glMultiTexEnviEXTPROC)((intptr_t)function_pointer);
	glMultiTexEnviEXT(texunit, target, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexEnvivEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	const GLint *params_address = (const GLint *)(intptr_t)params;
	glMultiTexEnvivEXTPROC glMultiTexEnvivEXT = (glMultiTexEnvivEXTPROC)((intptr_t)function_pointer);
	glMultiTexEnvivEXT(texunit, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexGendEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jdouble param, jlong function_pointer) {
	glMultiTexGendEXTPROC glMultiTexGendEXT = (glMultiTexGendEXTPROC)((intptr_t)function_pointer);
	glMultiTexGendEXT(texunit, coord, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexGendvEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jlong params, jlong function_pointer) {
	const GLdouble *params_address = (const GLdouble *)(intptr_t)params;
	glMultiTexGendvEXTPROC glMultiTexGendvEXT = (glMultiTexGendvEXTPROC)((intptr_t)function_pointer);
	glMultiTexGendvEXT(texunit, coord, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexGenfEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jfloat param, jlong function_pointer) {
	glMultiTexGenfEXTPROC glMultiTexGenfEXT = (glMultiTexGenfEXTPROC)((intptr_t)function_pointer);
	glMultiTexGenfEXT(texunit, coord, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexGenfvEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jlong params, jlong function_pointer) {
	const GLfloat *params_address = (const GLfloat *)(intptr_t)params;
	glMultiTexGenfvEXTPROC glMultiTexGenfvEXT = (glMultiTexGenfvEXTPROC)((intptr_t)function_pointer);
	glMultiTexGenfvEXT(texunit, coord, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexGeniEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jint param, jlong function_pointer) {
	glMultiTexGeniEXTPROC glMultiTexGeniEXT = (glMultiTexGeniEXTPROC)((intptr_t)function_pointer);
	glMultiTexGeniEXT(texunit, coord, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexGenivEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jlong params, jlong function_pointer) {
	const GLint *params_address = (const GLint *)(intptr_t)params;
	glMultiTexGenivEXTPROC glMultiTexGenivEXT = (glMultiTexGenivEXTPROC)((intptr_t)function_pointer);
	glMultiTexGenivEXT(texunit, coord, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexEnvfvEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetMultiTexEnvfvEXTPROC glGetMultiTexEnvfvEXT = (glGetMultiTexEnvfvEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexEnvfvEXT(texunit, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexEnvivEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetMultiTexEnvivEXTPROC glGetMultiTexEnvivEXT = (glGetMultiTexEnvivEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexEnvivEXT(texunit, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexGendvEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jlong params, jlong function_pointer) {
	GLdouble *params_address = (GLdouble *)(intptr_t)params;
	glGetMultiTexGendvEXTPROC glGetMultiTexGendvEXT = (glGetMultiTexGendvEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexGendvEXT(texunit, coord, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexGenfvEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetMultiTexGenfvEXTPROC glGetMultiTexGenfvEXT = (glGetMultiTexGenfvEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexGenfvEXT(texunit, coord, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexGenivEXT(JNIEnv *env, jclass clazz, jint texunit, jint coord, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetMultiTexGenivEXTPROC glGetMultiTexGenivEXT = (glGetMultiTexGenivEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexGenivEXT(texunit, coord, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexParameteriEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jint param, jlong function_pointer) {
	glMultiTexParameteriEXTPROC glMultiTexParameteriEXT = (glMultiTexParameteriEXTPROC)((intptr_t)function_pointer);
	glMultiTexParameteriEXT(texunit, target, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexParameterivEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong param, jlong function_pointer) {
	const GLint *param_address = (const GLint *)(intptr_t)param;
	glMultiTexParameterivEXTPROC glMultiTexParameterivEXT = (glMultiTexParameterivEXTPROC)((intptr_t)function_pointer);
	glMultiTexParameterivEXT(texunit, target, pname, param_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexParameterfEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jfloat param, jlong function_pointer) {
	glMultiTexParameterfEXTPROC glMultiTexParameterfEXT = (glMultiTexParameterfEXTPROC)((intptr_t)function_pointer);
	glMultiTexParameterfEXT(texunit, target, pname, param);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexParameterfvEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong param, jlong function_pointer) {
	const GLfloat *param_address = (const GLfloat *)(intptr_t)param;
	glMultiTexParameterfvEXTPROC glMultiTexParameterfvEXT = (glMultiTexParameterfvEXTPROC)((intptr_t)function_pointer);
	glMultiTexParameterfvEXT(texunit, target, pname, param_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexImage1DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint internalformat, jint width, jint border, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glMultiTexImage1DEXTPROC glMultiTexImage1DEXT = (glMultiTexImage1DEXTPROC)((intptr_t)function_pointer);
	glMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexImage1DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint internalformat, jint width, jint border, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glMultiTexImage1DEXTPROC glMultiTexImage1DEXT = (glMultiTexImage1DEXTPROC)((intptr_t)function_pointer);
	glMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexImage2DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glMultiTexImage2DEXTPROC glMultiTexImage2DEXT = (glMultiTexImage2DEXTPROC)((intptr_t)function_pointer);
	glMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexImage2DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glMultiTexImage2DEXTPROC glMultiTexImage2DEXT = (glMultiTexImage2DEXTPROC)((intptr_t)function_pointer);
	glMultiTexImage2DEXT(texunit, target, level, internalformat, width, height, border, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexSubImage1DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint width, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glMultiTexSubImage1DEXTPROC glMultiTexSubImage1DEXT = (glMultiTexSubImage1DEXTPROC)((intptr_t)function_pointer);
	glMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexSubImage1DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint width, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glMultiTexSubImage1DEXTPROC glMultiTexSubImage1DEXT = (glMultiTexSubImage1DEXTPROC)((intptr_t)function_pointer);
	glMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexSubImage2DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint format, jint type, jlong pixels, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)pixels;
	glMultiTexSubImage2DEXTPROC glMultiTexSubImage2DEXT = (glMultiTexSubImage2DEXTPROC)((intptr_t)function_pointer);
	glMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexSubImage2DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	const GLvoid *pixels_address = (const GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glMultiTexSubImage2DEXTPROC glMultiTexSubImage2DEXT = (glMultiTexSubImage2DEXTPROC)((intptr_t)function_pointer);
	glMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyMultiTexImage1DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint internalformat, jint x, jint y, jint width, jint border, jlong function_pointer) {
	glCopyMultiTexImage1DEXTPROC glCopyMultiTexImage1DEXT = (glCopyMultiTexImage1DEXTPROC)((intptr_t)function_pointer);
	glCopyMultiTexImage1DEXT(texunit, target, level, internalformat, x, y, width, border);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyMultiTexImage2DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint internalformat, jint x, jint y, jint width, jint height, jint border, jlong function_pointer) {
	glCopyMultiTexImage2DEXTPROC glCopyMultiTexImage2DEXT = (glCopyMultiTexImage2DEXTPROC)((intptr_t)function_pointer);
	glCopyMultiTexImage2DEXT(texunit, target, level, internalformat, x, y, width, height, border);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyMultiTexSubImage1DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint x, jint y, jint width, jlong function_pointer) {
	glCopyMultiTexSubImage1DEXTPROC glCopyMultiTexSubImage1DEXT = (glCopyMultiTexSubImage1DEXTPROC)((intptr_t)function_pointer);
	glCopyMultiTexSubImage1DEXT(texunit, target, level, xoffset, x, y, width);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCopyMultiTexSubImage2DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint x, jint y, jint width, jint height, jlong function_pointer) {
	glCopyMultiTexSubImage2DEXTPROC glCopyMultiTexSubImage2DEXT = (glCopyMultiTexSubImage2DEXTPROC)((intptr_t)function_pointer);
	glCopyMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, x, y, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexImageEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint format, jint type, jlong pixels, jlong function_pointer) {
	GLvoid *pixels_address = (GLvoid *)(intptr_t)pixels;
	glGetMultiTexImageEXTPROC glGetMultiTexImageEXT = (glGetMultiTexImageEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexImageEXT(texunit, target, level, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexImageEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint format, jint type, jlong pixels_buffer_offset, jlong function_pointer) {
	GLvoid *pixels_address = (GLvoid *)(intptr_t)offsetToPointer(pixels_buffer_offset);
	glGetMultiTexImageEXTPROC glGetMultiTexImageEXT = (glGetMultiTexImageEXTPROC)((intptr_t)function_pointer);
	glGetMultiTexImageEXT(texunit, target, level, format, type, pixels_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetMultiTexParameterfvEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetMultiTexParameterfvEXTPROC glGetMultiTexParameterfvEXT = (glGetMultiTexParameterfvEXTPROC)((intptr_t)function_pointer);
	glGetMultiT�٘9"���^'{!Ny���Y�q��n�OAd��%'B�*�UP���oO  �-KM�����x5�y�>�ZC�������}���h�����a����������Oh�� ��xO~�V��sw�,����ɾ �t����
��6�ڟ��9J�d��~�Fq(_RN�h��fz��G����x�@Q�T��(��5����a\?�p�׮��|�O|h�3=��j}�?�B���;�����z >;����ݨ\x�xՐy�/�X�A�N�9��qj�&-�����6�v�;u]���d�}�`�����<��OY��lq�+ɯH�E�Q�=,�d=��M$�s3���Όt�}w��d����TdVέ�U��z��52ЁY�Rm���x��g��&���bhSҋ�cȤ=�$Gu����L����i�6��s��}Q������B�G�Ip�+Vx�C�����܏����{��@e1*��;�p��.�Y�6������-(`z�,4��Z3~���]3������*9�9꽡���ac�˽mDM�P0l{8�|�4���x�
�� ��E,�qO�:����q��Ą��@bN,�t5�dQ#Q���@\�1��v.봗���t�/bA�z"~�S�7شDI�[��ܩ�PI�/��7��l�����ɸD���!T�cM�	Ʀ?S!�h�.�t�1��hLƪ�^~#������;�ɶ�v�\;���>������ t�x���X�>$��6�x�eԯ'�T�i��!͇�5P�W�kn��.p���L��r�~�w3��H�	T,��1qZ�N���,�`L�ty��Jy��@)"mAg/�v�L&N4�����ĳ�>����[��a�� ,fK�Īr�#zVB�4�kO��d�%:�-ZJ3��� ����a6W�dBj�N�)��֠���5�Cb5��S�f���k�"�OT����X��PUB��Y�3J~�*��<��[�;�0!����_�K��N���Ki��TH8��Bi�eYO�t��p�=�V�*��ݿp�:�c0�1�O��j������{��Mv=��Q|O��e�9��nF�x��5Ku��:�\D�
]V1[p<�1���ѐE� 9�E\U��u�@�VW���1����B��nZUI�Ğ��5hcʓ�jU/�l�p&81�CF�ҎN3@#?�X1���V����%;� |���E��UIA����[���(�̡~�N�1��
3�ZV���4��JV��D&��s�dWB8
�}#�5t�nM�S�r��]A@�4���˃���Øl�-p@�����)Q�p7b��7gu�,�ⷖ��� U%��0E�~���[�S&�_�y��>������\�nx�~~�F���/��h��(nR�e⫔Aei@Z�-PvS??�怑�T��a��p����L4�J�3f�5�8�]�e�}�]ķ�$!]ZЂ��C=S��W�c��?�����٬9�f�z0ށ����IZ�pt_�d/iL��[�ST���#����D� ^~�Ni���#�aL$�A���i�}5�+��}��՝��̬i� }�-��+��!�1eؼ�vQ�����-1
��A���K�g��KtW"�#h�z��@29���p'��2��/����!�tuv�d��7Li���0�
� ��?$V_�U��O'8fR!T��; �a8�$����5��
�����'�ho�ɬJ�؀~ۤ���Y�撷)�VF��y[��*�3r��V6����%�	m���	-&�'u�r�Dٕ�2">���4MW?�S�(��_"�0���K�4��%��
��9ÖZb�vlB� �Vʂ���t�9q�ʨf1 �A�[x���J��y��1�E�8!Z�d��9e�2(�3M�k��|�_	�{cPV��Yy�l�#=�y|�k� PL���K��E��aC'���^�Ws���$��� �:���#NJi;݆��n�*��w�. ��n�G�S�"��p��=� u�Ra������+W_�;g�/-,�9�������W�+��83��Z�`I���wMf/0ں�ՇU�>+�{���@�<1�zm��N��i8�Og�$��a�牻� �H.u
R�Z����'0ղ>�a�8&����VC#��ڌ,�*�JKy�2M�8|x]���ؼSD\1b�4�v8�Ke=�:�"DGDE�\Ƨ��D��u�B�t���)�g��j!TPC7��Z�L�mb̡��X������]�[��.0�X��T���4e/}���?�^�� Q��//���P���M�|Z<7*��1hq9++���ؤ�%����+����W;w���e��������X�w�4d����%)��@��)o�G`�R66�R��ǵ�J{n���
��dD+�3�Ͼ�����-*�~B	�22e������&<�����罪��%�%�I��L�#B��+����z��G���ṁG�H�����R��<��
~g�8 G���Rf7l���N,v��u[ߘ�]����Й�m��:8G��2Jǥ���v���ޒg��]k!�m�6ɮ#Uc�@��Es�H���&��j�~1Z�=&L�OH�U���g9Zs1�!�#�/��%)�l�Z��몡��GA���S�}*Ɠ��z$�X������ ��6�V��h�7�ư�V�jه㴜��Ql�<>���Ȇj��zг_�5@���nl�_E��);g¦/�2��kY2�|�2�Ήe��;���'�&q���,��WUa�Բ�0���U�������rڔ>��
� �+P�#��v�^7�}q���GXѣ�͵S��/��pޖE(�7=�H�1�n5ri�l ����[ w=�F�.M-��� �0H�E��m-�S(�
k�|]Y�e0m�IyB0�Ũ0!-��Z0�F�L����=�q�b�ꌁH;�W��|��3C��al�Ã��.�/;n�œ��u��q��H��҆=�y||���$�T��A�R�?B��?�6�������&�$lZ�-��mZN�m�<(
���J��G��<�6�t���,Q����g����W�i�#3�3�Z�<��;0��*�?�&���2٘+�[���Ҭ��}"��C3(@���mr�%h�_vo��T~�9G�^�����o�� �����Mgk����ϐœ@F�1GM��;�Q�d������#Y@u�����H��2�f���۴7����:V���o�Q�1�dg��]��?<$BC��M�tt��,�h�c����X/���F���"�����ݒj,�r��!�G�t�ău_MӨ�.9N���� �L��M�a�����O�փ�Ğ��CdW�r=)k6�aK�`��UV�P	���!l��B :z�k���Gdӯh,��6;��
�y�G�E|��"Wa����>�mW,w����K��݃�1i�y/�(d&���vЩ�?��?�MaDd8�]��E@0Sâ��Z��	k�������\aG�[��]ڷ����{m�&����ε2���cF��@;���`��f('��"�� �5���r�~W8��jϪ	}����Ɗ���j6���K-֝I��v�1��߹�-k���3OjѴ&bݠ����>�=&��C��(6O����E��3<5�FA� �����P��Y�����H*px�ή���ѡ�^�MN�o0�X�V���,x��Oy�a�bŤ�>d#��Ɂq�+���"i��\�	��>�Z	�S(+_?"F�Y��s�'EKFux?���7�5���{̌C���o��[^\;�`��F��±2����#���q��dU
:"rE����,e9���A�iٝ^�:����Q�>�
Nώ�I�,���Ѱ[E?&�9���}�L>�"�:��v�w�u(�B�l>�
�N�oJ:�8Rh��~����qq�w�C��la1卂�<�4N_���$ �G"���ui�\���|e��'*����.�C�P�ʹ�u<�[���D��k0�(��0��%���|&v�-Bm5W�W���6<�t�#���z����#��x��T�1������o`�Vtk@��F�䂣}�]���ס˦�H�d+�?N�Meu�ʦn/��<�����oAS0���Q�v�3��Ѱ/�ݝ��_iJ+�q�CcP	�q,������=Q��Tg�*�����ϩ:ZM�����'����Cbt�yt&�`�)��%�\繜̉4%@OK�0�A��]�!.&h���*W*|��{�p� �����Z���R�h�_����w����:~��K��u#�.�ל���b��h$Cx�נ69�sXB���1	�P��>�i�|��1�T����3���փ�.J��}2U���T�rm���J�2��?�6��v��d��܄ݺ{T���d�hG)�����b�QDR��>�t	�\�)A������������SD�:a�Me��,>���b?0�:^K7�u�9�V<���J3B�'����-g� ���d8K�R��B#{g�kq쬑r�����ʣ{��n|ϠLh3G��!�L;�¤�F7
���p&��Twl#c�.(v�ڼ�ٔ	�Ě5���Xv>��	��@�N�m��wk��	a �����ؑ�h��K��� C#�^Ӓ�K������ �m��s|���X�h�ȸ���\v���0�rh���$��'8���y3h�p���,�>2��5�^�&�M7�"�>� 
��T��[��%[��K6T�$���`�8zpͧ�,Zh,��Up�qlk��{��ض���2|��}�6R^˓[�Ff�o�g\if�G�w�0�.eԌ�/~�p��^	����S �<7�O#�!��ñ6/%8'E�E����Olx�����d����i�`����ȳ_�Y+�Q���f5����4�/É ��"}*���vy�;�Y.Ŕ�j�S=��c�.�`�^�H%��/i��|Ma;�a�� ���u�@����[��Bx�Z0��� :]xP�5�k~U���e$�Z��2l�*��6i2	c���H��5�+n &�%'�=�"@A<V|S���1O>�zOzd�S5c	�ep���?����2`��{����Y��o���=Q��A�'*��H��n�BJ�Q�*�yjYJڧ?_��cQʜ�k���Ut˰�r���O��-�k1ʪ�&eЩh %��@��FV�x��j~<�]8�مO
<�~.�Qug�3d�*AJL�D<�����dy�l�]����L�Q���V�0�'��:��_�#k����3P4�N]n7�-�a�Mc��*P�*���P�cXJN)���(����mƼ�2@J;^�1���4�&����BMg���1�Agfz��U�x?��S����jI��~~S�V]���jtDWl��2�e/g]�X���.�9�b�V��p[�u&��������И��6���9oL�;��`�fH�^@˅X~�),iq�K��$5#�.�$����L���1"�:SSgK���"�=",��>]h�����U�R1��}.��_u�<�+�/-d��Z/Q��Wh���	��zqR��H�/�\#�eW�FH��an���|��9�q󧳢r�*�py�o���N3���b?*G��6�+�$bp��/%Ą< �t����v���3���%�ef���Mͨ�zJ��x�2V)�P�s��6��d�~��C�mM�G63�Ình{��Qv���"�_�iW�ny�Awۖ���-%sQ��97�g/J��ǌ�)x,L�5�|�
�O2�@�'=������Z���*͗v�{mW����h�� ����a�.�s[}~��4<��9���>I ��!�T�Ě|Qtl۹>�r@MI�Ϛ���Y�6o��l�ݪB�J��a�G��՚�Y_����\�$�#���35�l�3�9%
%��c�J�d_�p7��N]��Õ�K�<k�T�4Rz-r�]��|���9m̾4D�Og�p*��,ox.�ܲb��j���'n�Ҽ�zc��wM��M���L�H0�=��д�����0�	�����Li+�Ǝ���O������9Zds�T�[TC_���^���L�u0N�2�����V,a�"W���\�[9v���w���*����p���L�Me�^���I���R�J�Z�5����r��:��d�D=��u2W��Q3����kᡩ��S[t~J�\F=���W����<8�T	� �ie�AF�6�"�;
ٰ�j�J�u��T[�!��\pξc.m���H�s�4p�,�Ui��9Ϙl��VS��C�'O�ֿ7���R4hԫ޼��y<q���*Vާ�vd���[��W�&��]�$�z�E����ƍs�ד�	^�=�{�ȣ�3���!^��5L��"[P��l��+����cť3�{-��m/{���W�#�� �cV����x$}��e�i�O�*�I���W%�����0��8�C�g������jD�����+a��5��vk%Wo����X���.'�=)�*/�|jg��?��A,
K Z��b�!���[⁘�U�4��ڗ+�H�_in�hL�����O�[�K�^�\��>��F�:^��Hq?�}�#��t��G��g��Y�U�3��~<Y'�ܨx:J�h�0[}�#��Z���Ԉ�P\� 1��U��zeg�e���]������3
�s(M8��l��.F�!�k�n���Lrn0�2}���~��
��Z�7M�d@R��H&��#6�2�lq��z�Ķ-����4ټPp2��U��5׮���gRdis�=i<�ݮ���]/GP�����Y�f��,e�U����e�ii�LI�F�0�b���>x!!7������rES��	�A.���7;
�G�mP��\:�~\�v��b"ԏJ��=��йiMN��:Băt��+�)�A]��a��Da�������.[�mˠ����f=+�bw}O�r��x���!&�wϰ��ݚڛ������� �\�J��2}JpÖ^`�G�˷ڰB�) 'O�z����ǽ�<ܥ�L:IΆ��g:������;��d�y�I���ˌMj�������p.�W�%I �,�I�\d�++ e�D�3V[�4�� 'S���ͬS��!c	=Ӧ���	�3���3 ��$�Ɛ�zG�'�x����6��kθ)`�Z-��(j �VR�. ���v�����!��f�u9ˇB�|��}]�?�hS\��W��\O�L�>A�C��6�'��g@��ߺ:	�SX�籁�@����D
ո]Rr/C��j�+��j(�B���h�v7׈�,;��y}�j�=������w�sqg��E����D^C�SMH
-R-�&�1KCb|�nr��<�R�#�LlR
a8 ">�d��x��� O0'N�l�ʐcŮ�mQ�!"��q��U��$�W.]̿��~R��ϸ��ǘ����G�[$fF��Y:#��/�����w��ҙ�E�n;��i6g_䵮PFR��6(
~}���{h��8*���o9��ela�~$t�o<�Sh���D�4����=��e���*4}g!����\����h����W�עjĦO}�[���'\�����J�g�y�����.Q�W���G����;��~�	��:��=�,�$��3��������W-�*�i��v�=���֊�A�Xx�g�4x��ԏ]��M��hQ�ȿ�ԙV��E��"s�nVG-��P5&��$�iG���Cm�c[k4$�f0����6�Jɣ�\��Y�jR���ڀo�R�������ꦭt�B�H$L3p�0��g�uD�Y7�4 �A�����0M��U��ۮn}$�;�76��#k|��wn%g9����ɏ�X� ��y��$�F�L	���ۈ7.�w��l'�q8�:Q7�0�f(+�����?t�>���T�#'-�cI��q�E�1�WG����'Z�2�؇��x����̀��:U0����v�D��$i_vEXT = (glGetPointeri_vEXTPROC)((intptr_t)function_pointer);
	GLvoid * __result;
	glGetPointeri_vEXT(pname, index, &__result);
	return safeNewBuffer(env, __result, result_size);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedProgramStringEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint format, jint len, jlong string, jlong function_pointer) {
	const GLvoid *string_address = (const GLvoid *)(intptr_t)string;
	glNamedProgramStringEXTPROC glNamedProgramStringEXT = (glNamedProgramStringEXTPROC)((intptr_t)function_pointer);
	glNamedProgramStringEXT(program, target, format, len, string_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedProgramLocalParameter4dEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jdouble x, jdouble y, jdouble z, jdouble w, jlong function_pointer) {
	glNamedProgramLocalParameter4dEXTPROC glNamedProgramLocalParameter4dEXT = (glNamedProgramLocalParameter4dEXTPROC)((intptr_t)function_pointer);
	glNamedProgramLocalParameter4dEXT(program, target, index, x, y, z, w);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedProgramLocalParameter4dvEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jlong params, jlong function_pointer) {
	const GLdouble *params_address = (const GLdouble *)(intptr_t)params;
	glNamedProgramLocalParameter4dvEXTPROC glNamedProgramLocalParameter4dvEXT = (glNamedProgramLocalParameter4dvEXTPROC)((intptr_t)function_pointer);
	glNamedProgramLocalParameter4dvEXT(program, target, index, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedProgramLocalParameter4fEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jfloat x, jfloat y, jfloat z, jfloat w, jlong function_pointer) {
	glNamedProgramLocalParameter4fEXTPROC glNamedProgramLocalParameter4fEXT = (glNamedProgramLocalParameter4fEXTPROC)((intptr_t)function_pointer);
	glNamedProgramLocalParameter4fEXT(program, target, index, x, y, z, w);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedProgramLocalParameter4fvEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jlong params, jlong function_pointer) {
	const GLfloat *params_address = (const GLfloat *)(intptr_t)params;
	glNamedProgramLocalParameter4fvEXTPROC glNamedProgramLocalParameter4fvEXT = (glNamedProgramLocalParameter4fvEXTPROC)((intptr_t)function_pointer);
	glNamedProgramLocalParameter4fvEXT(program, target, index, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedProgramLocalParameterdvEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jlong params, jlong function_pointer) {
	GLdouble *params_address = (GLdouble *)(intptr_t)params;
	glGetNamedProgramLocalParameterdvEXTPROC glGetNamedProgramLocalParameterdvEXT = (glGetNamedProgramLocalParameterdvEXTPROC)((intptr_t)function_pointer);
	glGetNamedProgramLocalParameterdvEXT(program, target, index, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedProgramLocalParameterfvEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jlong params, jlong function_pointer) {
	GLfloat *params_address = (GLfloat *)(intptr_t)params;
	glGetNamedProgramLocalParameterfvEXTPROC glGetNamedProgramLocalParameterfvEXT = (glGetNamedProgramLocalParameterfvEXTPROC)((intptr_t)function_pointer);
	glGetNamedProgramLocalParameterfvEXT(program, target, index, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedProgramivEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetNamedProgramivEXTPROC glGetNamedProgramivEXT = (glGetNamedProgramivEXTPROC)((intptr_t)function_pointer);
	glGetNamedProgramivEXT(program, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedProgramStringEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint pname, jlong string, jlong function_pointer) {
	GLvoid *string_address = (GLvoid *)(intptr_t)string;
	glGetNamedProgramStringEXTPROC glGetNamedProgramStringEXT = (glGetNamedProgramStringEXTPROC)((intptr_t)function_pointer);
	glGetNamedProgramStringEXT(program, target, pname, string_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedTextureImage3DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint depth, jint border, jint imageSize, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glCompressedTextureImage3DEXTPROC glCompressedTextureImage3DEXT = (glCompressedTextureImage3DEXTPROC)((intptr_t)function_pointer);
	glCompressedTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedTextureImage3DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint depth, jint border, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedTextureImage3DEXTPROC glCompressedTextureImage3DEXT = (glCompressedTextureImage3DEXTPROC)((intptr_t)function_pointer);
	glCompressedTextureImage3DEXT(texture, target, level, internalformat, width, height, depth, border, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedTextureImage2DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint imageSize, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glCompressedTextureImage2DEXTPROC glCompressedTextureImage2DEXT = (glCompressedTextureImage2DEXTPROC)((intptr_t)function_pointer);
	glCompressedTextureImage2DEXT(texture, target, level, internalformat, width, height, border, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedTextureImage2DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint height, jint border, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedTextureImage2DEXTPROC glCompressedTextureImage2DEXT = (glCompressedTextureImage2DEXTPROC)((intptr_t)function_pointer);
	glCompressedTextureImage2DEXT(texture, target, level, internalformat, width, height, border, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedTextureImage1DEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint border, jint imageSize, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glCompressedTextureImage1DEXTPROC glCompressedTextureImage1DEXT = (glCompressedTextureImage1DEXTPROC)((intptr_t)function_pointer);
	glCompressedTextureImage1DEXT(texture, target, level, internalformat, width, border, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedTextureImage1DEXTBO(JNIEnv *env, jclass clazz, jint texture, jint target, jint level, jint internalformat, jint width, jint border, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedTextureImage1DEXTPROC glCompressedTextureImage1DEXT = (glCompressedTextureImage1DEXTPROC)((intptr_t)function_pointer);
	glCompressedTextureImage1DEXT(texture, target, level, internalformat, width, bo�<��ɾ�c��|A�>	��C$Y���(�f��w���Rm�0�u"Ku_�'���1��B}9��G�#$�L��"s���n�P��)�H����iib�!\��5�ȋ��l�X�)��rS@��g�����݃5�9� �gH��%XL�Њ��Q�CXs���8K�;�b�X���j7F�3"��*I]�!�?��ߨ���]&�F�&��\�U鋘��2���Q�9-]%�����B	w>���/����IȚ��S�A]6�q��9R)ĥ�J��T�1�N� �g�b����ŧ��L��B�y�f�+�_�";9i�c���#(w�.XkU�{�cIo �r�(��̊F���U΅΍SJ����o$��C�ר�}0@e�K�1ـ9fA�e�y	�̓�Ip}���5|k��{�JmfC�6޶�%�=�%j@��@w�V���	��9�c�ՕIM��낃�}�X����9���t�c�Q�	,W	=rO�����h�*���w�����Y}5�j��OQ8/��'��D-Y��Pm�v���XuĀ�T��0
0id�����u1M�Bx}P�֢8���^!'��i��P��}��`��$�����}N���t�	��B��Z���q֎�qߗ��<�@���ʛ���~2?�O��dP.��s_��*��'�OS�Ԩ��Y@�2��E[�8lX.y�=P�!X���ӽ�QL����5To�d.�{'N�y��Qo�l,�t�gn�!8^��,h���Af#_uL?(�+=�B�u����)�;)���_�P?�^�)�P��'�^�Cx�G����W3�gT�j�[�\S�o���[�����Y� ���������z#z,.�d��+���7}���B�/~y{܉c�5H�yK�����m���`D�B�]�)^(��Hsq&�ϡ�S
��,YS
�x}��JA���.r�.�q{�ySZ�'[H��O�kr4�}@ax��p>F9J���3�#�v��y7�������~�Lh��(�@�1��c�(�R�n���E���$x@��F��gg��"�J��Z;P��`���~��bJ���h&�2u�|ֲs�)�'$���� ���ߟ�n��0�'մ3YM�d�%��D����ll�rlJ�����}Q�L��	|�8M�3��)�=7�,���B�=U�o<�S�°��S���L�N�T��4͓� �!�I·�/���꿤�9��sYV[��$C@�aG�W ���H1f�ϩ�q���Ο��y�Kr)É�(ݙۦ��
�	m�*�`�}�W�5_'��[����"�:���x��'��0�~�C,�Jο�(��[�GDhka2tv����X}N�W�w^ ��Tk�u��/��
K�c��U	��o%�17�_��_�c�;���y����AZ���G��13���W�b�wwB_�kw�����h{$�CH�
�q��^�HP��zM��uuh`��7�Ē�+{jwnȎ�!�RͥA\Ր�a�~��U>�0�݅�)��޺����;��xL��CX��	��l�Z�Q�����0��b�pʺlC<��L�P,����|9'1E��t�JX�����Ǜ�,�vS��f7��^c���usn*C�:��5�� q��@Mў��ǬP���O�@9�f97{�����?��mH����;�VyG@o���镔��M��6�6.UJ)q�$�!�W�)�֓@Zy	�|��dRL\�#X
��r�4�b���%�7i�t�C)�ޯܐ���iN�Q!�
K���b~_��#���`���*7��u�p�.���iÏɡkG���:�C�=�>��~d99'�D$sV ��jG����åS��BTUk���pˑ�?���&y�p3�l��[�OM"����=��]�E:Ih:���vW9��W-��!�q�lq�0�QH��i	8�tg��Aw�M�K2�)�cN��+qO f����EiD��k��ܩ�o/-^�� ���;Iݤ����,4e)33NJKq�.<rc��͟���L1����Q��z^MfS�0����S�b�]�:l u�F�*�}�c[�G��%p���Te!aj�u2��UȍV_�eȿ�j��������f`K�䛹N���d�����C�.��S`I2���*��X�ND��_�F��@��@���ENZ��E�(�|��}�w n�篁��|)e����/b�{�0��DՆV)g�~��x	\CH/u�)���	��]A�J�U�d�.C�bdJ��C�����ٸ�@%�y��lZ�\�������^��FF���(�������d��k���T�5K����&��)B�����Y�(��%�nX��/5�	�[�FMO�~=QXq������μ_{���l��u�,;0�'�w�p�����L<�J]1-@s#��:�Up.�Q���G3e�H����:��U�$B5��&�H<��c��S�E�.����}o1�>�p^X"��*�;Г�\��G�#S��ָuJF���%�d�q�2�����H��aA |[�h��vƅ!�&���X�ѪT>�I���0.���7A�$Y��ڣ�����:�:��ey GTAbԶ'~߿$���v�d�mܯ��ҝw>JnW`�6^W��3/�bH��PHKq�0D�Ť�:|4�F�g���,z9�鳣^��_.6ؚ&�������ݨkH>��L!G$�I���IY��������A'bd2Y	辣a�����#���T����{�|^󅵀 0�� V�-Ṡ4���,�QCK�g�
hs �C�'�Ot��a�W���[�J���7x�Rv:��☈87Ұ�z�I��� 'g���ucώ{n�M���Ǒeκ(	����;�l��u�-��^�`s�
m�, �ΰ��0$����9�Z�Œǽl)l>�=$_����Jȫ�e�!Q'�5}���[y�4�F�jEM�==!�+z��5���F��oe��Œg�0���+c� HB<0��p&�ܔ S ��Z�ڴ��ƅB�,H׷|�d�F�ԋ��*�ϧ�¡�	e�����%��-뙟�������w�@��"�;��,���V�M�i����uCV�Zr�x+hr[��� �X�z7I8v����u�����Q�*��]���b��L��q}IW��AvR��������,*�O�Y�G��4���gB��0�P�srL�X�|�, Y�	�H,KU����xGU* ��I_Y��9TdS�X�XГ�"�ad)K21t�9�x��G�+^=fE����h_Y�3{y� ��d"΢��ѭH=��N8���ғBu?���+�N����N$�u2��==N9/~;?�жz+��N�NDhq��a��v	��-Vt�lX�̆���N(���4���P�<cHnkS���)*���j�a��L5�5��&z'O&���:���d�B{�Η���T��P�E��ʥ�=T�K�<��E1����Å|Q%��Ϛ<C�?g��JG��p�C��ۆ�5�RԴ|k�c#��]b��<-S��b���1��v��^�(JV5�Y_�隰Vx�LB��^����<!��ɋn���Z�X'�5��P��I�~�/����V���ķ�Ӑ��Q�f�n_��'(5H䑇m��;8q��0��~���_��w�T�\X����v�q���*��Vy�!��[��^l~\����`�J8*�P���,�֣���ǣӅ�_����3�`�����R������xo:�o�h^d+H����Vn��A_L�H��v+|� d���5>���Jn�3��E��	�UJ�(t��e��!"؞��IH�re�}G&�t&��#*���z���.?W���wD�2�X�2����ۜ73�D7N�E�����> �$��]�'�
ǩ����+��L}�d�;u'\��	�*�l���hX�+Ҳ׭�Fw���М��\��C����A��w�C]���6��p4��A&�,o��j��oViζV�!��u��$�D�/�guĖSk�O���Ιd��*�ƾQ��j���� ~k��i�E(�fn(��͓�_h��Ip� �{�1�Y�0.���;���ǰ�D�R�'(�C/��H)OŊ�l�4��c��&�&�'�Q6kYV6�!�����Jf?֡dl�+��|��5x���O�a��.�u�����p^Q�l��>��s�Y�>��^BM��F�������%�\m�"�GR�bMP����	�Z�Iz��O�Nq0� 1�B^y� ���[7O�'>��ih� �q����O���S$+M�#O�%���Z䱉��(��$�D�|l�����C	/2���>Љ��X����x�j2�k J��k1W���7�9�=� �u�2�c�j������i�7�Q�O�q�T�D�+0Fb�r�T�ϔ>�>UhLH�A�oCd�_�o�ϣ�1F�����݆n҇ӚG��B��7`�VjO�A�51��7%�>�7��Ķ�@~u���L��q�uj^�s΃�:�<�`ScL��Mu�?r.�ȝ(��7�G�ڐ]N��щ��v�Y�V������d�5R7��_N"(]dH6�ף��u�xӃ�a���ʱnKM���@n�y��_ ��az�p��ʑCHŤ�O��Xrh@� �Կ�tF�Asl���S���\0W��A��W��m��8�� �ʦ˘z�؂W�	��<A��A��{Û�p��ц&�C��������++>7�F��xgm��fX��n��-�[X:�خ��R}��s��	yL>y7�gR�HQsء�霵z`�籥!I2a��q.r�<��Bj�f&�^�Dd�2k��cp��"�,M8fH`m,,�J2�Q�j����6��=�,?���0wo�(G�)7��gڛ7�p�h9p�G��� �]�*+�{�e�W�z��V��ć�x��ɰ����Q��{`gb�b��J"���?���E�j~#r|ȉ$o�&�{���;���w�W�M�<]n��U=ro�ޱ�	ҫxHXX�dh�Y�W���%vC�g�~~�-S\[�N/A��@�-t+�l?(t��:%@3G���$%�-�c��?.�������d�@.��%�	�0ڞi�j�]�)���"���>v��Z��mJg��"���G/��.���UӜ)yF�ݦME�-o�W��4z�(�Y/���N��6�����j�3��;���\G�)���J�<���A}�ehZ�ڼ��hX�	C��h�nm�͍�o�-���Et�7`�t��J�Ȏ�����2�g�#f��>���^��n��S����7�(ʋvsF�#�k[�C	�L-���l7!Q����|� ���ِ��&J��-��Ӥ��t�en��e�]�P�K,ֿK��Ӹ7�}�<!��x��5��@<�o�T'����+��C�GTiyb3c����SzI��>M=��/�t��<��B�r�Y�F��x:�er�W��k�J�m�N���R��	��@L��'7��1t���B�`�U@�"Y}�����}e�jF��z��N�pS"��xuөdi8^l������-YlaBն��MҰWJ¸�Y�o��_�$��ۋ��ذ��s�����=������?��VV]U����� ��A'�9Χ"C}��[�x���2!b+E��8�\௩_���Ւ�o�qIƝ`?��L~گ�0=pc^�&��r��BN;��A��ĴB��X���I�|�xp0b���+��z��-����=�S4sb��Z�ؙ���F�y�45^_ 7�G�\�W��\��BPbD�-��P|S�3Jz;��6	�?�e����*e�^~�$�����gS�J�+E���{SN�� ���l��� �9�Ƣq�U�/���X��ÄwO��G�cV�e�$�>��yI26�TSKmO˞>o�0���� ��U^@o���Pۗ�'���@XӅI �ŏvf�I7�ٽ�8��G�O(Lk'���|7o�����Q�\+R��]L�Б5�gkP��iL�W�Vn�A�J]� ��//
p��߲	ov�� rҰ���/Dq�����;Uٿ����&>a!>G?MRu�9n=����� �\+Ϥ��o3��Pj1w\r�H�����@+�-t�*D68yv�Q��n�XB���_h��j<`[�_��WƐ7&r�U���-Ʈẁ���JiX�插T���j�鷻����B[ ;�?��y��X�kJ��I���l��Q���![XΖt?�z �[��3�6xl�寁��izz�����i+�f�<��GޖLl3�7��uFNDI-d�c���G^��_L�F�V�~�fO�qiL��E��������]g�3��8[����ʌ�S�����i��皻͟�k��.���Y�	L�͢��)��!����J�V�n��(Y�bB��a-��\��qgh9_ m��������T5��ճE��u�cakL�s�`�w����Q�0p*Q/5��Y�R-Fi�Y���O7>S�1����9��s�/x=��&�x��`�?��yX��<����Z#�	�&eTt<��L�%���u��I�6ԡ��1f���0<�`�gY5����8�����tV@gjd�M��A��g�8���#^���0T�L���-"���!%�jZ9����������w��h~1F({ԣpN��C��	�#�<�kӼ����:3[4{�rRP��(/�) ��LWf�yꌩ�37q�D�3���)����Z��KYY����,��ӓҘ�wa��AUm"�J�>��t�������r@O\'}'ݥ�a�Ė��y;�� �@�����d�m~����'�Ĉq�		Y����#�`�zrz�"�W2a�EU��;?��R�˷�P�U���5p�OR?����� 9ٰ�.�S�T��v(b����z$Ô#+�J��Њaֺ(	����4�n��!K�)��B�n�Na�/'��&,�Ɓ�:����^�Ð�``M8�4E`Z����LF¡�]K�!h�*|���Ke�`�$i�yEJ�=\D�?j8��:���n��x6����e�?���f�TJuvӑjr��0NMأ�Y��ͣ�ڞ��/H��L�Q�]�Ԫ��3�����XҪ����E��/������ ���
�w�}��?���Ќ�&�q�I��	���}W.V�]b�^2~Rb���\�z�#^/a����m#��y�W�&6��)y�� �f�D�ڎBAXw���lO壏�^�Ͱ:,(�w��l��nD���W���P�eu�y�f�)X��D.z�����MpA]*m��AJG��5T$_�P�M5Ù�<�` s2lw�G���z�dj��6ѫwx�tTK���6!��*�̸^ ��w���l��oH)������e��ɚN
�I`��89u8a&�߂c��J�}_hq��;��K��yP�y�����3�@4���k���H�!:~tqo���^�:��J�;υ@5�^:��y)mM3ؼ�W6���h�M`���������_
�F՚���i�@�y֕<������(^;����7P�?L��FH���L�����6�I��3�h2��Xe��s-P��ue�Ɋ��u��^�Hq�T��ۉBr�JN��!���'*̗ɷ/���Q�z���O����p�����c�����_��ܗ�t%�j��V�'qz7���e��DW����x���N4�~�y� �Pm���|�I�b���x��5\p�6��L��^bCX����I�u	;�5���R������ٶŴ�T�����k�������f����k]�/�4G
bH����So��w@M�V��jv"w�2��4���Kn���|��3�R{�$r����}3Ԟ��D�gv�VB*�}5��c&õ�h���Zb.Q���/H�*�Q�.����ȟ7�;�F�����vD� ��+�N�}��������%�
Ӎ
AHD��6'��l���v{�lǿ֦�EZ���ɑ��F��b���p��R�Ty����8�T4��q%�2I��|��dGx&	 ƅV۫��^t�1�P�/�M�Hx��E%�Ԗ���}��=�ӡSϞf=����udc�O�w�6�|{z�����!��r�M,�0�{�[�~ ؚrnalformat, jint width, jint border, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedMultiTexImage1DEXTPROC glCompressedMultiTexImage1DEXT = (glCompressedMultiTexImage1DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexImage1DEXT(texunit, target, level, internalformat, width, border, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedMultiTexSubImage3DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint width, jint height, jint depth, jint format, jint imageSize, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glCompressedMultiTexSubImage3DEXTPROC glCompressedMultiTexSubImage3DEXT = (glCompressedMultiTexSubImage3DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedMultiTexSubImage3DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint zoffset, jint width, jint height, jint depth, jint format, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedMultiTexSubImage3DEXTPROC glCompressedMultiTexSubImage3DEXT = (glCompressedMultiTexSubImage3DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexSubImage3DEXT(texunit, target, level, xoffset, yoffset, zoffset, width, height, depth, format, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedMultiTexSubImage2DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint format, jint imageSize, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glCompressedMultiTexSubImage2DEXTPROC glCompressedMultiTexSubImage2DEXT = (glCompressedMultiTexSubImage2DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedMultiTexSubImage2DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint yoffset, jint width, jint height, jint format, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedMultiTexSubImage2DEXTPROC glCompressedMultiTexSubImage2DEXT = (glCompressedMultiTexSubImage2DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexSubImage2DEXT(texunit, target, level, xoffset, yoffset, width, height, format, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedMultiTexSubImage1DEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint width, jint format, jint imageSize, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glCompressedMultiTexSubImage1DEXTPROC glCompressedMultiTexSubImage1DEXT = (glCompressedMultiTexSubImage1DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCompressedMultiTexSubImage1DEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jint xoffset, jint width, jint format, jint imageSize, jlong data_buffer_offset, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)offsetToPointer(data_buffer_offset);
	glCompressedMultiTexSubImage1DEXTPROC glCompressedMultiTexSubImage1DEXT = (glCompressedMultiTexSubImage1DEXTPROC)((intptr_t)function_pointer);
	glCompressedMultiTexSubImage1DEXT(texunit, target, level, xoffset, width, format, imageSize, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetCompressedMultiTexImageEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jlong img, jlong function_pointer) {
	GLvoid *img_address = (GLvoid *)(intptr_t)img;
	glGetCompressedMultiTexImageEXTPROC glGetCompressedMultiTexImageEXT = (glGetCompressedMultiTexImageEXTPROC)((intptr_t)function_pointer);
	glGetCompressedMultiTexImageEXT(texunit, target, level, img_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetCompressedMultiTexImageEXTBO(JNIEnv *env, jclass clazz, jint texunit, jint target, jint level, jlong img_buffer_offset, jlong function_pointer) {
	GLvoid *img_address = (GLvoid *)(intptr_t)offsetToPointer(img_buffer_offset);
	glGetCompressedMultiTexImageEXTPROC glGetCompressedMultiTexImageEXT = (glGetCompressedMultiTexImageEXTPROC)((intptr_t)function_pointer);
	glGetCompressedMultiTexImageEXT(texunit, target, level, img_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixLoadTransposefEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLfloat *m_address = (const GLfloat *)(intptr_t)m;
	glMatrixLoadTransposefEXTPROC glMatrixLoadTransposefEXT = (glMatrixLoadTransposefEXTPROC)((intptr_t)function_pointer);
	glMatrixLoadTransposefEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixLoadTransposedEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLdouble *m_address = (const GLdouble *)(intptr_t)m;
	glMatrixLoadTransposedEXTPROC glMatrixLoadTransposedEXT = (glMatrixLoadTransposedEXTPROC)((intptr_t)function_pointer);
	glMatrixLoadTransposedEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixMultTransposefEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLfloat *m_address = (const GLfloat *)(intptr_t)m;
	glMatrixMultTransposefEXTPROC glMatrixMultTransposefEXT = (glMatrixMultTransposefEXTPROC)((intptr_t)function_pointer);
	glMatrixMultTransposefEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMatrixMultTransposedEXT(JNIEnv *env, jclass clazz, jint matrixMode, jlong m, jlong function_pointer) {
	const GLdouble *m_address = (const GLdouble *)(intptr_t)m;
	glMatrixMultTransposedEXTPROC glMatrixMultTransposedEXT = (glMatrixMultTransposedEXTPROC)((intptr_t)function_pointer);
	glMatrixMultTransposedEXT(matrixMode, m_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedBufferDataEXT(JNIEnv *env, jclass clazz, jint buffer, jlong size, jlong data, jint usage, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glNamedBufferDataEXTPROC glNamedBufferDataEXT = (glNamedBufferDataEXTPROC)((intptr_t)function_pointer);
	glNamedBufferDataEXT(buffer, size, data_address, usage);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedBufferSubDataEXT(JNIEnv *env, jclass clazz, jint buffer, jlong offset, jlong size, jlong data, jlong function_pointer) {
	const GLvoid *data_address = (const GLvoid *)(intptr_t)data;
	glNamedBufferSubDataEXTPROC glNamedBufferSubDataEXT = (glNamedBufferSubDataEXTPROC)((intptr_t)function_pointer);
	glNamedBufferSubDataEXT(buffer, offset, size, data_address);
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMapNamedBufferEXT(JNIEnv *env, jclass clazz, jint buffer, jint access, jlong result_size, jobject old_buffer, jlong function_pointer) {
	glMapNamedBufferEXTPROC glMapNamedBufferEXT = (glMapNamedBufferEXTPROC)((intptr_t)function_pointer);
	GLvoid * __result = glMapNamedBufferEXT(buffer, access);
	return safeNewBufferCached(env, __result, result_size, old_buffer);
}

JNIEXPORT jboolean JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglUnmapNamedBufferEXT(JNIEnv *env, jclass clazz, jint buffer, jlong function_pointer) {
	glUnmapNamedBufferEXTPROC glUnmapNamedBufferEXT = (glUnmapNamedBufferEXTPROC)((intptr_t)function_pointer);
	GLboolean __result = glUnmapNamedBufferEXT(buffer);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedBufferParameterivEXT(JNIEnv *env, jclass clazz, jint buffer, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetNamedBufferParameterivEXTPROC glGetNamedBufferParameterivEXT = (glGetNamedBufferParameterivEXTPROC)((intptr_t)function_pointer);
	glGetNamedBufferParameterivEXT(buffer, pname, params_address);
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedBufferPointervEXT(JNIEnv *env, jclass clazz, jint buffer, jint pname, jlong result_size, jlong function_pointer) {
	glGetNamedBufferPointervEXTPROC glGetNamedBufferPointervEXT = (glGetNamedBufferPointervEXTPROC)((intptr_t)function_pointer);
	GLvoid * __result;
	glGetNamedBufferPointervEXT(buffer, pname, &__result);
	return safeNewBuffer(env, __result, result_size);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedBufferSubDataEXT(JNIEnv *env, jclass clazz, jint buffer, jlong offset, jlong size, jlong data, jlong function_pointer) {
	GLvoid *data_address = (GLvoid *)(intptr_t)data;
	glGetNamedBufferSubDataEXTPROC glGetNamedBufferSubDataEXT = (glGetNamedBufferSubDataEXTPROC)((intptr_t)function_pointer);
	glGetNamedBufferSubDataEXT(buffer, offset, size, data_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform1fEXT(JNIEnv *env, jclass clazz, jint program, jint location, jfloat v0, jlong function_pointer) {
	glProgramUniform1fEXTPROC glProgramUniform1fEXT = (glProgramUniform1fEXTPROC)((intptr_t)function_pointer);
	glProgramUniform1fEXT(program, location, v0);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform2fEXT(JNIEnv *env, jclass clazz, jint program, jint location, jfloat v0, jfloat v1, jlong function_pointer) {
	glProgramUniform2fEXTPROC glProgramUniform2fEXT = (glProgramUniform2fEXTPROC)((intptr_t)function_pointer);
	glProgramUniform2fEXT(program, location, v0, v1);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform3fEXT(JNIEnv *env, jclass clazz, jint program, jint location, jfloat v0, jfloat v1, jfloat v2, jlong function_pointer) {
	glProgramUniform3fEXTPROC glProgramUniform3fEXT = (glProgramUniform3fEXTPROC)((intptr_t)function_pointer);
	glProgramUniform3fEXT(program, location, v0, v1, v2);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform4fEXT(JNIEnv *env, jclass clazz, jint program, jint location, jfloat v0, jfloat v1, jfloat v2, jfloat v3, jlong function_pointer) {
	glProgramUniform4fEXTPROC glProgramUniform4fEXT = (glProgramUniform4fEXTPROC)((intptr_t)function_pointer);
	glProgramUniform4fEXT(program, location, v0, v1, v2, v3);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform1iEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint v0, jlong function_pointer) {
	glProgramUniform1iEXTPROC glProgramUniform1iEXT = (glProgramUniform1iEXTPROC)((intptr_t)function_pointer);
	glProgramUniform1iEXT(program, location, v0);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform2iEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint v0, jint v1, jlong function_pointer) {
	glProgramUniform2iEXTPROC glProgramUniform2iEXT = (glProgramUniform2iEXTPROC)((intptr_t)function_pointer);
	glProgramUniform2iEXT(program, location, v0, v1);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform3iEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint v0, jint v1, jint v2, jlong function_pointer) {
	glProgramUniform3iEXTPROC glProgramUniform3iEXT = (glProgramUniform3iEXTPROC)((intptr_t)function_pointer);
	glProgramUniform3iEXT(program, location, v0, v1, v2);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform4iEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint v0, jint v1, jint v2, jint v3, jlong function_pointer) {
	glProgramUniform4iEXTPROC glProgramUniform4iEXT = (glProgramUniform4iEXTPROC)((intptr_t)function_pointer);
	glProgramUniform4iEXT(program, location, v0, v1, v2, v3);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform1fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniform1fvEXTPROC glProgramUniform1fvEXT = (glProgramUniform1fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform1fvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform2fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniform2fvEXTPROC glProgramUniform2fvEXT = (glProgramUniform2fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform2fvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform3fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniform3fvEXTPROC glProgramUniform3fvEXT = (glProgramUniform3fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform3fvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform4fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniform4fvEXTPROC glProgramUniform4fvEXT = (glProgramUniform4fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniform4fvEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform1ivEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLint *value_address = (const GLint *)(intptr_t)value;
	glProgramUniform1ivEXTPROC glProgramUniform1ivEXT = (glProgramUniform1ivEXTPROC)((intptr_t)function_pointer);
	glProgramUniform1ivEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform2ivEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLint *value_address = (const GLint *)(intptr_t)value;
	glProgramUniform2ivEXTPROC glProgramUniform2ivEXT = (glProgramUniform2ivEXTPROC)((intptr_t)function_pointer);
	glProgramUniform2ivEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform3ivEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLint *value_address = (const GLint *)(intptr_t)value;
	glProgramUniform3ivEXTPROC glProgramUniform3ivEXT = (glProgramUniform3ivEXTPROC)((intptr_t)function_pointer);
	glProgramUniform3ivEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniform4ivEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jlong value, jlong function_pointer) {
	const GLint *value_address = (const GLint *)(intptr_t)value;
	glProgramUniform4ivEXTPROC glProgramUniform4ivEXT = (glProgramUniform4ivEXTPROC)((intptr_t)function_pointer);
	glProgramUniform4ivEXT(program, location, count, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix2fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix2fvEXTPROC glProgramUniformMatrix2fvEXT = (glProgramUniformMatrix2fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix2fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix3fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix3fvEXTPROC glProgramUniformMatrix3fvEXT = (glProgramUniformMatrix3fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix3fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix4fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix4fvEXTPROC glProgramUniformMatrix4fvEXT = (glProgramUniformMatrix4fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix4fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix2x3fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix2x3fvEXTPROC glProgramUniformMatrix2x3fvEXT = (glProgramUniformMatrix2x3fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix2x3fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix3x2fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix3x2fvEXTPROC glProgramUniformMatrix3x2fvEXT = (glProgramUniformMatrix3x2fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix3x2fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix2x4fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix2x4fvEXTPROC glProgramUniformMatrix2x4fvEXT = (glProgramUniformMatrix2x4fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix2x4fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix4x2fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix4x2fvEXTPROC glProgramUniformMatrix4x2fvEXT = (glProgramUniformMatrix4x2fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix4x2fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix3x4fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix3x4fvEXTPROC glProgramUniformMatrix3x4fvEXT = (glProgramUniformMatrix3x4fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix3x4fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglProgramUniformMatrix4x3fvEXT(JNIEnv *env, jclass clazz, jint program, jint location, jint count, jboolean transpose, jlong value, jlong function_pointer) {
	const GLfloat *value_address = (const GLfloat *)(intptr_t)value;
	glProgramUniformMatrix4x3fvEXTPROC glProgramUniformMatrix4x3fvEXT = (glProgramUniformMatrix4x3fvEXTPROC)((intptr_t)function_pointer);
	glProgramUniformMatrix4x3fvEXT(program, location, count, transpose, value_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureBufferEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint internalformat, jint buffer, jlong function_pointer) {
	glTextureBufferEXTPROC glTextureBufferEXT = (glTextureBufferEXTPROC)((intptr_t)function_pointer);
	glTextureBufferEXT(texture, target, internalformat, buffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexBufferEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint internalformat, jint buffer, jlong function_pointer) {
	glMultiTexBufferEXTPROC glMultiTexBufferEXT = (glMultiTexBufferEXTPROC)((intptr_t)function_pointer);
	glMultiTexBufferEXT(texunit, target, internalformat, buffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureParameterIivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong params, jlong function_pointer) {
	const GLint *params_address = (const GLint *)(intptr_t)params;
	glTextureParameterIivEXTPROC glTextureParameterIivEXT = (glTextureParameterIivEXTPROC)((intptr_t)function_pointer);
	glTextureParameterIivEXT(texture, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureParameterIuivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong params, jlong function_pointer) {
	const GLuint *params_address = (const GLuint *)(intptr_t)params;
	glTextureParameterIuivEXTPROC glTextureParameterIuivEXT = (glTextureParameterIuivEXTPROC)((intptr_t)function_pointer);
	glTextureParameterIuivEXT(texture, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureParameterIivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetTextureParameterIivEXTPROC glGetTextureParameterIivEXT = (glGetTextureParameterIivEXTPROC)((intptr_t)function_pointer);
	glGetTextureParameterIivEXT(texture, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetTextureParameterIuivEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint pname, jlong params, jlong function_pointer) {
	GLuint *params_address = (GLuint *)(intptr_t)params;
	glGetTextureParameterIuivEXTPROC glGetTextureParameterIuivEXT = (glGetTextureParameterIuivEXTPROC)((intptr_t)function_pointer);
	glGetTextureParameterIuivEXT(texture, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexParameterIivEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	const GLint *params_address = (const GLint *)(intptr_t)params;
	glMultiTexParameterIivEXTPROC glMultiTexParameterIivEXT = (glMultiTexParameterIivEXTPROC)((intptr_t)function_pointer);
	glMultiTexParameterIivEXT(texunit, target, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexParameterIuivEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint pname, jlong params, jlong function_pointer) {
	const GLuint *params_address = (const GLuint *)(intptr_t)params;
	glMultiTexParameterIuivEXTPROC glMultiTexParameterIuivEXT = (glMultiTexParameterIuivEXTPROC)((intptr_t)function_pointer)X0s��efY�䯜�c�^��	K�&��
xB)&��]�P`��5� n7_o�%�p�7h]9^wi��3�6sT�_m�-r4�[U��(�>���(/3����� ���d��F���8'I��w�;��n��2�5X�K��Upi2��\ t��$:Mn�ȭK��Ci���wp�����)�7ެ�9I��a3Ԡr��[�~��b7]�W3�A�Z�3l�]X�3q�;ROf� L�����n{�.�����K�]���CU�.�;�� �7�C"��JB��/U<�=`?�&�2�E(��,P�z���M�G7Q0	���'��̤ȵ�#g;^#O� !��'�<y�>ݵ��Xm|��ׄ�RjUz��m�,KT��w����ti��(u^�\:7N�ߥ\1Q���e+O�D����ΰ��/X���N�Da��������͗��-}���QzR�'��|�vD���W�ARFM�e���p&4�g=�M����@r�O��. �vTXrF���(�#�p�Z%p�b�o���Q�>x=��u+�Y�p^���b$�-O��X/~��qy:���NJ��T	6��J��^'�|�_R��Zl3y�"®b��(F�~壦�&��~/�B���i6N��N�qa#��]�g�6yhW��*"��<4�n����H!�͘g���"�'`���UXͽ�k�	�R�Vd�
�r��e��o0�q��Tױ�ww�m	���a(��kbJ���"��fF�je�cЍ���9>V��<�C,d��ޝ�_\=Y�=�7&�o�f�[�6,������1Ͷ-8�&�O�
z]+Kp�+��-�i�>����-�_�0�mAdm&2��LD��<���o8��6�7P�֨oX,Xm��,�k|foGJ�б�҇a1|�5!��k�X��N�E��+O�7eM��ـ�C����W�+�f��>�vc�X�u�V11<֕7�B�wS�u7�BQ��ޝ�L�/&Z�<�N<F�1��\�:��N܍ 	�Xg�`.��TDm]ek�ǻI��R��`�(dö�M�<V��Er��M�P��Q�_���!�U�����3����К�bQ'���ﱒ�U�ӽ��q�>3i{�V�7{re��,����O�%.�;˃��V,�'W���E�vY��N�D���7+����|����D�.�\���H���'�B�E�[��C֒\��	�E�f�Z4"; ~��t�/����ȂʉkUemt�{O���P}��)��(�(e��z0N�E�;�3�
7ڞ�j�W�,��R���_t�bhN���KE�0�R�|�Yb���5Qqփ4�Q���}/xC���S�y�3�g��ļ��VS���*vӾ�N+���T��U�9��]�-�|�����ֱr�ϓ�@Z�/�C����1���Iح	NE�
�Z%Q��K��y�n��1Ϋ,�T��m60u���\�2��6�|A�
��ڑ#(�Xpl��d�+�C�0�Z���K	�����6��""�P�e�����$�a[����op�C��7��m~BY\����C���7ֵ�@4�3r��*��Z' A;Y�
�^�}D���D��ʍV�?��A؛k	�~�j]X�N���ϲB�#�5�v�w����w\�1Z��NxN�>���&��G��{�=�^�L���8+G���QGZ3]�h�+P�6��	�ө���Y�DU�#�Y��P/<!g�T���]��٧�>WH)��
h��.x�+O�f�_���fzn�`���V�����^�4s��qW@�R73��<c��#��Q���&����#Я
#S\d��>�������{Wl=6�"B�����S���U���j��h�!�X���� =�OO�U�$)�s��ɉ	E5�����PhH��
�[.���k'�'=>�/��|�x_U�;��y!ȈK��^֕��16��H��}$��)�3�D��X�j�1-��J�J9��Ү=B�'���뻇}!7�'v5�6��V���#&�L�����������zɾ��2�
�Fܻ l]��<	�.̤����J94gK�u��������!Ҟ�q,fХ��C<9�4�����2��<j�y�M�����gd�������X�D�BF��~�/�3e#�U�fPA���06�������llF36ɻ����,ż�0"��v����%K�%�6���,��l��y�Gc�Z�! 2�({�SJ���l����]�ߛt�<j�����Q�"�,%��S֚�2�7�?b�!��0�5��G]��Ì	�QEڄ��սS����^
�jgz�u�&u�G
�:�q&�U���Q�V��B���Ym�j�`�������s�|j}��򧕡L[j�By^A,M�<�p`s�VyM��5�_�F�!}MY++`�{�"�	�"-3JE'�������7]J�e��0��Q�ַ+�w"֧��2G��闫0�ȡ۬��C�>X/uO��D	+)��2)��ҁ��#��騟|�I��;seC���TLX��(9{j�,��;\Y��s"��݇l4��q%�ji.�/s�]&��F�j�󟪖3C��g���13Myb�����U��������[�W��~h��VN�Q�]��d����Jg��p�z�m����.X<�P���\����"��^���X����ɡ����鿸R&��^�����/�Ӄ���=_�uo�j�( X"��"̾u�/�v��zS/�ԁ��Q��E׊Z�Lbd��B;��	�+/^��� m�BfGHV�Dk�Z	���6�x���v8ӊ�&J1~�ڵ��g	�T���׺�Ń�;�N�\U;;ww�8��i�<
�Z��F�����%��W��͂Pw'��0O�G�[�Ob8kA��������ʛRߟ�<r>�[�AQ
���Ė4,�9'��%<�v)�b)��bm*�����0u�#�F͓V�+u&�xC��F�b"d��D�B��8�*U�5�����g��i������%�ֲ�L�C���9���1?���@+g34N����n��t�OCd��}+�+�_�F��iG� W\������1��!�XW���#���u���@�6������ �����𰋒�Nl��1d�:̉HTS���kc�r�֝-�j������>a�&ͨ��l�U��v�!M wiV_�b��x/��n����;�FG�
@��'�o�9����'C#�F%��S� �bq�5B���rv�k�A���t����M�e69��Ƞ��`y�FgȔ%�#�4�n�4���hfc�7-����r�|�&!L�A��x�V�.�����sՖF}��Z����p�6�
=V�2y��`v,�`&��ߖ@�GB��s����FTb��B��o��$#��S�dF���4��d�
�3���YFdɜ�^���GC�o�������x�7Ψ6��2���$���C�KP�)�L_c�W�����懵ܖ�`��]?T��!�(��+�U�=������-*yvM�%��]	m����h'y������NU9�\�����B�onÙ�P#'1�4sIt=j�T�&���2�O���V2� m�?���t���񷅒WpP&�~�}jQ&S���hB<�t��.v ���Ҵ�W�	'8�ZU�'�Wi��~X�]�����XU�[b��0p��D���P��ʿ^筰RL�b�Ǽ@O+�c�g�7�R��N���Zu"������i���өN�B.���1ϺM�
׺��=�2���}�$�9w�l�rƱEC�:��|�K���*L�iD_�$-�ʵ�6E���/��a�|�C%U��c�i.��:Qz�o���9�l]�Ph��|R��u
1?Il76�m�J ߢɩ&����-����#��"��?#\�м|�e2XG�*�mO���0�>2�H5ay �؊f���
�adk�[lC(�P�)�������<�"R��r�|6���l�7ˬMTŴ����4��EWn]��D�?:�?��-��X�=�[/d�����r�W���3��^X:�n4�H7�B��7��t�ܪƅ }�ߌ��]��Y��C����~��st��P>g~��z��� � q��9It�9�3�}B�KDK.JM�����^�9�.q!�ɼ^(��]D�Ops�%����D���jZ]R�����Di	xˀ�Yq�e�?g&1�BEoD���A+6�Qo�������I\�B(�8��1��TCQ�խ�h���|���?�,c����EG���4��z:W��UYݺ�sLU
�a90�$9�}P �A�3��[VF�,����Ϝ�����Xh����12%g�A@{C|��O������Q/��0Z�3���]�}w4��t��/������$�|]�|;�R��F&��y�3�
)Lb�'窎V1}j@�?Lw^&�᱕uID�+��~��1H��痿<�]�,i�#�'�Z�7F��f���|U����eӈ�����:B�㳯�΁dQ�k�hځ�����j3�9DQc�0�"f�3���i;ϚކF�'Zl�c|����eA�P���`%�+ZE�/��]�������a�r�&��	.+/�-�7m���\v��:���_T,_E��E���J�3ݱ_`Nz�&Y�5��L"%��*�)��+�����*�Vn5�m��)$���6�W� ?᪷+$C@�L��Q/$u"�=hT�$v�9���1���ė�%�s0���cC��������e�悷2�x��Ps��*�+tõ.ˠ�X� �q���	:�Cz�y�D��7'4���5;#�Q;�"���2�Ҙ ���0��1V��A��8O�]q@��)�O��[�ȸn�mb�ٍh4p�J�3���_�� �1�T�-I�d��\W1�G��0D�X'��m�nH�vof����0��d�g|�z�>LA��i��`�'8�&���D�mu�K�{�Y����7���x*/]ً��c�"��[�s�e��A�d�=��U4 ��=�4�Qg���ǯ�:FH�?�m�5�����N�.�Q�1��T8��B�-���wM;?�!��߶��|�q5��8�J�:=�g��-�l��$���R֢���g�g
z$}����:ן�b�/3a����~7W��؊,�D"�K;P6T�m2�s0O�ᴷ�!7$7[�>�k4�KS��0D'ob&�01����;��s�TW�y�Ɂ8�x��;FY0SX<��}�_�X-����*�Ô��K�F�H��-�Iϧ���T�ǢfFrz����`��EU��qj�ٕ����f+{/��iC.Auw/_&������o��������Bc{��	�1��������X�w�4l����Vk��K�� |�Wq�G*�U�Q���G�.	����Fzk�<��������cUZ�{5������3+�;;���������
�z�-���_�w^U�����>�f츺��G���X��V�Y���Z��q��p��Yf �uWm/Y���Gb/f�İ�H;$"�<�W@����������d�Ŵ9�њ,;����h눞Ϗ\��SxW�|��'r��b~�@��C�O^���"8��k�u7�H�*a�u���|Xy���qW���<)�|�zK�ړ����H E�YÛZ�#dΑ��pe�\������H�,���B�_����8�@�ɔ���Qw�} ����C��kϪe�4x��ls��w�Hn/��%��1�jys�v�5��_��2l9���(�&\���#��wQ}�Ո'>��ȝ@� �L���*��^+��/ٻ��	s`� ��;�Qm�5U���||�����S��{�n�/ޟ@'	�rs�^�1�b#<c�5n��B��NGb(��4y����UeL�_�Y�];�I#�C9�5$s�i9e�X-)\�²d+#��D&��W��P�y�z�H{����C&���5�������?-����A�1n����t��d��B�X��!�4ia������v$� �	���5;�� �����q2�fA+bF�4�4�iz�1�=m[���`��Q��s�y�z�.�H�w���2�:�����m
�^���/��F'�� �b�%-�տ��#��ݔ���7e5��J
J���bw�'n�<SX��R;�-b�B�2����5u��,�����zX&=槦�����[A�1B�	�?�`�o���펬$.Dr�����D��3�$�����N���ذ��g#X�B�/�kt�k�H��C?pa
��T�of��c�a�Z���T�J"�����������؅{5�A7��9�v�l(���K���+nx@���� ���:�L� ����m�㥌����
@moĥLl+&`�WJ�'��FI�/���5��*'(�$���Hd��'T#��u�g�~��cg��,^[�٣���-ctf������ш�/je�y9U�zo"��S�Z�����O�k"5|�W��E>O���O��u*�������s3A�/η	�����zI�i���Ϻ��4���~Mî'��!��*�;h+��:�H�4奥k�x$h��j��@@��V��Ή���a)�۬L9-ۛ��|����2�ģ�Oh��aDk��5#ɲ�֌]�>�(1�I�
�$m8�ڔ��;��uGP1�/.�9���J��]��v�E��߶\m%um{����IO�֬@�S]�^A�z�W� ��v��z�,�x��!u ��˴q�?���Qx��I�)��/��P:fvm�X���2pGs4a2���1��t��u��v�=��.��y@9�)��L����K<���������#\^/"6k����7b1A���[�uh��=`����ԙ�%�s���Y��淉�L]"��}���iH�@6�(�,���m�
rm�G�|?��e�f ^=��w7xnf��w���j~�:�k�� %����b�\8WL��:�>/� ���V2�6���:�ʩ>0��/��*��2�ܣ�t`X1�i���G��j#�;����6�)��PJ	t/�S\?� ���->�M�)���k���1�*��N���r$������s+�]zKN��
�鑸jH�R쾒��؛�/	�:d�$A�A1`�ߤw��<�����9Lq���[M�c��|����b������d>a�p�9u!�A̃����BFC��My�?����򜃁fmݱ��� �����Hu=�`G'��"�7�%�q���ۍ*\F'`�#�p���=4Y��9�p=��I�|AC������P��C�O�H�W����<���
����
l�>(�Xʛ���7��k1EERI3���4J��� �#ň4�i�(��1�R�/���J�L����^dڝz/!�ÍH�O ��F�b�9��5�,��v��y��܈��kK���n�/@<Jȍ��c�HIɥQ�k:��j{i
�����������B�E�t|k��94�vќZ14�~Dw�F�#U+�Y2���C0M�8���5E��t���O (z�X��@+D[�W*m죘c���ǯ˙y!��w@�%*q���Z
���T�\Fy?����acH��qXq:c:&�/ 6(����Uܪ���p���EX-��/���c�e��n"H�� Or�����߅���[�̦tR6�vЅ�R���*��a�N�w�Q�T ���a&�H�ƥ�їvZۨϥH�3#ˠ�:�� 2>���&c��h�3���8"�� �i�S�-�8�&�! m��S��R��'~��2l+�@���H�"Zk/B⇴:@<c��G9�~kBӽ\�j�������>u�Թg�~Vt46�w�}U�(�|-,�g���kW��
�/ ~�l��
rJ���+ym!�61�U/�/����z,'w\In�Eڀ��Qdp3�����y����'`�Z��c����E�X���f+���y~�"�jݮ��
 }$����wn�>�\7���c�t3��N�8�L�f�+D?��0%��(
6�)}����~�/F����k��4�n#���amedProgramLocalParameterIivEXTPROC)((intptr_t)function_pointer);
	glGetNamedProgramLocalParameterIivEXT(program, target, index, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedProgramLocalParameterIuivEXT(JNIEnv *env, jclass clazz, jint program, jint target, jint index, jlong params, jlong function_pointer) {
	GLuint *params_address = (GLuint *)(intptr_t)params;
	glGetNamedProgramLocalParameterIuivEXTPROC glGetNamedProgramLocalParameterIuivEXT = (glGetNamedProgramLocalParameterIuivEXTPROC)((intptr_t)function_pointer);
	glGetNamedProgramLocalParameterIuivEXT(program, target, index, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedRenderbufferStorageEXT(JNIEnv *env, jclass clazz, jint renderbuffer, jint internalformat, jint width, jint height, jlong function_pointer) {
	glNamedRenderbufferStorageEXTPROC glNamedRenderbufferStorageEXT = (glNamedRenderbufferStorageEXTPROC)((intptr_t)function_pointer);
	glNamedRenderbufferStorageEXT(renderbuffer, internalformat, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedRenderbufferParameterivEXT(JNIEnv *env, jclass clazz, jint renderbuffer, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetNamedRenderbufferParameterivEXTPROC glGetNamedRenderbufferParameterivEXT = (glGetNamedRenderbufferParameterivEXTPROC)((intptr_t)function_pointer);
	glGetNamedRenderbufferParameterivEXT(renderbuffer, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedRenderbufferStorageMultisampleEXT(JNIEnv *env, jclass clazz, jint renderbuffer, jint samples, jint internalformat, jint width, jint height, jlong function_pointer) {
	glNamedRenderbufferStorageMultisampleEXTPROC glNamedRenderbufferStorageMultisampleEXT = (glNamedRenderbufferStorageMultisampleEXTPROC)((intptr_t)function_pointer);
	glNamedRenderbufferStorageMultisampleEXT(renderbuffer, samples, internalformat, width, height);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedRenderbufferStorageMultisampleCoverageEXT(JNIEnv *env, jclass clazz, jint renderbuffer, jint coverageSamples, jint colorSamples, jint internalformat, jint width, jint height, jlong function_pointer) {
	glNamedRenderbufferStorageMultisampleCoverageEXTPROC glNamedRenderbufferStorageMultisampleCoverageEXT = (glNamedRenderbufferStorageMultisampleCoverageEXTPROC)((intptr_t)function_pointer);
	glNamedRenderbufferStorageMultisampleCoverageEXT(renderbuffer, coverageSamples, colorSamples, internalformat, width, height);
}

JNIEXPORT jint JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglCheckNamedFramebufferStatusEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint target, jlong function_pointer) {
	glCheckNamedFramebufferStatusEXTPROC glCheckNamedFramebufferStatusEXT = (glCheckNamedFramebufferStatusEXTPROC)((intptr_t)function_pointer);
	GLenum __result = glCheckNamedFramebufferStatusEXT(framebuffer, target);
	return __result;
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferTexture1DEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint textarget, jint texture, jint level, jlong function_pointer) {
	glNamedFramebufferTexture1DEXTPROC glNamedFramebufferTexture1DEXT = (glNamedFramebufferTexture1DEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferTexture1DEXT(framebuffer, attachment, textarget, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferTexture2DEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint textarget, jint texture, jint level, jlong function_pointer) {
	glNamedFramebufferTexture2DEXTPROC glNamedFramebufferTexture2DEXT = (glNamedFramebufferTexture2DEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferTexture2DEXT(framebuffer, attachment, textarget, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferTexture3DEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint textarget, jint texture, jint level, jint zoffset, jlong function_pointer) {
	glNamedFramebufferTexture3DEXTPROC glNamedFramebufferTexture3DEXT = (glNamedFramebufferTexture3DEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferTexture3DEXT(framebuffer, attachment, textarget, texture, level, zoffset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferRenderbufferEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint renderbuffertarget, jint renderbuffer, jlong function_pointer) {
	glNamedFramebufferRenderbufferEXTPROC glNamedFramebufferRenderbufferEXT = (glNamedFramebufferRenderbufferEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferRenderbufferEXT(framebuffer, attachment, renderbuffertarget, renderbuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetNamedFramebufferAttachmentParameterivEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint pname, jlong params, jlong function_pointer) {
	GLint *params_address = (GLint *)(intptr_t)params;
	glGetNamedFramebufferAttachmentParameterivEXTPROC glGetNamedFramebufferAttachmentParameterivEXT = (glGetNamedFramebufferAttachmentParameterivEXTPROC)((intptr_t)function_pointer);
	glGetNamedFramebufferAttachmentParameterivEXT(framebuffer, attachment, pname, params_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGenerateTextureMipmapEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jlong function_pointer) {
	glGenerateTextureMipmapEXTPROC glGenerateTextureMipmapEXT = (glGenerateTextureMipmapEXTPROC)((intptr_t)function_pointer);
	glGenerateTextureMipmapEXT(texture, target);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGenerateMultiTexMipmapEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jlong function_pointer) {
	glGenerateMultiTexMipmapEXTPROC glGenerateMultiTexMipmapEXT = (glGenerateMultiTexMipmapEXTPROC)((intptr_t)function_pointer);
	glGenerateMultiTexMipmapEXT(texunit, target);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglFramebufferDrawBufferEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint mode, jlong function_pointer) {
	glFramebufferDrawBufferEXTPROC glFramebufferDrawBufferEXT = (glFramebufferDrawBufferEXTPROC)((intptr_t)function_pointer);
	glFramebufferDrawBufferEXT(framebuffer, mode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglFramebufferDrawBuffersEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint n, jlong bufs, jlong function_pointer) {
	const GLenum *bufs_address = (const GLenum *)(intptr_t)bufs;
	glFramebufferDrawBuffersEXTPROC glFramebufferDrawBuffersEXT = (glFramebufferDrawBuffersEXTPROC)((intptr_t)function_pointer);
	glFramebufferDrawBuffersEXT(framebuffer, n, bufs_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglFramebufferReadBufferEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint mode, jlong function_pointer) {
	glFramebufferReadBufferEXTPROC glFramebufferReadBufferEXT = (glFramebufferReadBufferEXTPROC)((intptr_t)function_pointer);
	glFramebufferReadBufferEXT(framebuffer, mode);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetFramebufferParameterivEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint pname, jlong param, jlong function_pointer) {
	GLint *param_address = (GLint *)(intptr_t)param;
	glGetFramebufferParameterivEXTPROC glGetFramebufferParameterivEXT = (glGetFramebufferParameterivEXTPROC)((intptr_t)function_pointer);
	glGetFramebufferParameterivEXT(framebuffer, pname, param_address);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedCopyBufferSubDataEXT(JNIEnv *env, jclass clazz, jint readBuffer, jint writeBuffer, jlong readoffset, jlong writeoffset, jlong size, jlong function_pointer) {
	glNamedCopyBufferSubDataEXTPROC glNamedCopyBufferSubDataEXT = (glNamedCopyBufferSubDataEXTPROC)((intptr_t)function_pointer);
	glNamedCopyBufferSubDataEXT(readBuffer, writeBuffer, readoffset, writeoffset, size);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferTextureEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint texture, jint level, jlong function_pointer) {
	glNamedFramebufferTextureEXTPROC glNamedFramebufferTextureEXT = (glNamedFramebufferTextureEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferTextureEXT(framebuffer, attachment, texture, level);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferTextureLayerEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint texture, jint level, jint layer, jlong function_pointer) {
	glNamedFramebufferTextureLayerEXTPROC glNamedFramebufferTextureLayerEXT = (glNamedFramebufferTextureLayerEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferTextureLayerEXT(framebuffer, attachment, texture, level, layer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglNamedFramebufferTextureFaceEXT(JNIEnv *env, jclass clazz, jint framebuffer, jint attachment, jint texture, jint level, jint face, jlong function_pointer) {
	glNamedFramebufferTextureFaceEXTPROC glNamedFramebufferTextureFaceEXT = (glNamedFramebufferTextureFaceEXTPROC)((intptr_t)function_pointer);
	glNamedFramebufferTextureFaceEXT(framebuffer, attachment, texture, level, face);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglTextureRenderbufferEXT(JNIEnv *env, jclass clazz, jint texture, jint target, jint renderbuffer, jlong function_pointer) {
	glTextureRenderbufferEXTPROC glTextureRenderbufferEXT = (glTextureRenderbufferEXTPROC)((intptr_t)function_pointer);
	glTextureRenderbufferEXT(texture, target, renderbuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMultiTexRenderbufferEXT(JNIEnv *env, jclass clazz, jint texunit, jint target, jint renderbuffer, jlong function_pointer) {
	glMultiTexRenderbufferEXTPROC glMultiTexRenderbufferEXT = (glMultiTexRenderbufferEXTPROC)((intptr_t)function_pointer);
	glMultiTexRenderbufferEXT(texunit, target, renderbuffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayVertexOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint size, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayVertexOffsetEXTPROC glVertexArrayVertexOffsetEXT = (glVertexArrayVertexOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayVertexOffsetEXT(vaobj, buffer, size, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayColorOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint size, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayColorOffsetEXTPROC glVertexArrayColorOffsetEXT = (glVertexArrayColorOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayColorOffsetEXT(vaobj, buffer, size, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayEdgeFlagOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayEdgeFlagOffsetEXTPROC glVertexArrayEdgeFlagOffsetEXT = (glVertexArrayEdgeFlagOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayEdgeFlagOffsetEXT(vaobj, buffer, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayIndexOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayIndexOffsetEXTPROC glVertexArrayIndexOffsetEXT = (glVertexArrayIndexOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayIndexOffsetEXT(vaobj, buffer, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayNormalOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayNormalOffsetEXTPROC glVertexArrayNormalOffsetEXT = (glVertexArrayNormalOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayNormalOffsetEXT(vaobj, buffer, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayTexCoordOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint size, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayTexCoordOffsetEXTPROC glVertexArrayTexCoordOffsetEXT = (glVertexArrayTexCoordOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayTexCoordOffsetEXT(vaobj, buffer, size, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayMultiTexCoordOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint texunit, jint size, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayMultiTexCoordOffsetEXTPROC glVertexArrayMultiTexCoordOffsetEXT = (glVertexArrayMultiTexCoordOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayMultiTexCoordOffsetEXT(vaobj, buffer, texunit, size, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayFogCoordOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayFogCoordOffsetEXTPROC glVertexArrayFogCoordOffsetEXT = (glVertexArrayFogCoordOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayFogCoordOffsetEXT(vaobj, buffer, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArraySecondaryColorOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint size, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArraySecondaryColorOffsetEXTPROC glVertexArraySecondaryColorOffsetEXT = (glVertexArraySecondaryColorOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArraySecondaryColorOffsetEXT(vaobj, buffer, size, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayVertexAttribOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint index, jint size, jint type, jboolean normalized, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayVertexAttribOffsetEXTPROC glVertexArrayVertexAttribOffsetEXT = (glVertexArrayVertexAttribOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayVertexAttribOffsetEXT(vaobj, buffer, index, size, type, normalized, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglVertexArrayVertexAttribIOffsetEXT(JNIEnv *env, jclass clazz, jint vaobj, jint buffer, jint index, jint size, jint type, jint stride, jlong offset, jlong function_pointer) {
	glVertexArrayVertexAttribIOffsetEXTPROC glVertexArrayVertexAttribIOffsetEXT = (glVertexArrayVertexAttribIOffsetEXTPROC)((intptr_t)function_pointer);
	glVertexArrayVertexAttribIOffsetEXT(vaobj, buffer, index, size, type, stride, offset);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglEnableVertexArrayEXT(JNIEnv *env, jclass clazz, jint vaobj, jint array, jlong function_pointer) {
	glEnableVertexArrayEXTPROC glEnableVertexArrayEXT = (glEnableVertexArrayEXTPROC)((intptr_t)function_pointer);
	glEnableVertexArrayEXT(vaobj, array);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglDisableVertexArrayEXT(JNIEnv *env, jclass clazz, jint vaobj, jint array, jlong function_pointer) {
	glDisableVertexArrayEXTPROC glDisableVertexArrayEXT = (glDisableVertexArrayEXTPROC)((intptr_t)function_pointer);
	glDisableVertexArrayEXT(vaobj, array);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglEnableVertexArrayAttribEXT(JNIEnv *env, jclass clazz, jint vaobj, jint index, jlong function_pointer) {
	glEnableVertexArrayAttribEXTPROC glEnableVertexArrayAttribEXT = (glEnableVertexArrayAttribEXTPROC)((intptr_t)function_pointer);
	glEnableVertexArrayAttribEXT(vaobj, index);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglDisableVertexArrayAttribEXT(JNIEnv *env, jclass clazz, jint vaobj, jint index, jlong function_pointer) {
	glDisableVertexArrayAttribEXTPROC glDisableVertexArrayAttribEXT = (glDisableVertexArrayAttribEXTPROC)((intptr_t)function_pointer);
	glDisableVertexArrayAttribEXT(vaobj, index);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetVertexArrayIntegervEXT(JNIEnv *env, jclass clazz, jint vaobj, jint pname, jlong param, jlong function_pointer) {
	GLint *param_address = (GLint *)(intptr_t)param;
	glGetVertexArrayIntegervEXTPROC glGetVertexArrayIntegervEXT = (glGetVertexArrayIntegervEXTPROC)((intptr_t)function_pointer);
	glGetVertexArrayIntegervEXT(vaobj, pname, param_address);
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetVertexArrayPointervEXT(JNIEnv *env, jclass clazz, jint vaobj, jint pname, jlong result_size, jlong function_pointer) {
	glGetVertexArrayPointervEXTPROC glGetVertexArrayPointervEXT = (glGetVertexArrayPointervEXTPROC)((intptr_t)function_pointer);
	GLvoid * __result;
	glGetVertexArrayPointervEXT(vaobj, pname, &__result);
	return safeNewBuffer(env, __result, result_size);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetVertexArrayIntegeri_1vEXT(JNIEnv *env, jclass clazz, jint vaobj, jint index, jint pname, jlong param, jlong function_pointer) {
	GLint *param_address = (GLint *)(intptr_t)param;
	glGetVertexArrayIntegeri_vEXTPROC glGetVertexArrayIntegeri_vEXT = (glGetVertexArrayIntegeri_vEXTPROC)((intptr_t)function_pointer);
	glGetVertexArrayIntegeri_vEXT(vaobj, index, pname, param_address);
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglGetVertexArrayPointeri_1vEXT(JNIEnv *env, jclass clazz, jint vaobj, jint index, jint pname, jlong result_size, jlong function_pointer) {
	glGetVertexArrayPointeri_vEXTPROC glGetVertexArrayPointeri_vEXT = (glGetVertexArrayPointeri_vEXTPROC)((intptr_t)function_pointer);
	GLvoid * __result;
	glGetVertexArrayPointeri_vEXT(vaobj, index, pname, &__result);
	return safeNewBuffer(env, __result, result_size);
}

JNIEXPORT jobject JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglMapNamedBufferRangeEXT(JNIEnv *env, jclass clazz, jint buffer, jlong offset, jlong length, jint access, jobject old_buffer, jlong function_pointer) {
	glMapNamedBufferRangeEXTPROC glMapNamedBufferRangeEXT = (glMapNamedBufferRangeEXTPROC)((intptr_t)function_pointer);
	GLvoid * __result = glMapNamedBufferRangeEXT(buffer, offset, length, access);
	return safeNewBufferCached(env, __result, length, old_buffer);
}

JNIEXPORT void JNICALL Java_org_lwjgl_opengl_EXTDirectStateAccess_nglFlushMappedNamedBufferRangeEXT(JNIEnv *env, jclass clazz, jint buffer, jlong offset, jlong length, jlong function_pointer) {
	glFlushMappedNamedBufferRangeEXTPROC glFlushMappedNamedBufferRangeEXT = (glFlushMappedNamedBufferRangeEXTPROC)((intptr_t)function_pointer);
	glFlushMappedNamedBufferRangeEXT(buffer, offset, length);
}

