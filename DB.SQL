CREATE TABLE public.roles
(
    id_rol serial,
    nombre_rol character varying,
    id_usuarioauditoria integer,
    CONSTRAINT roles_id_rol_pk PRIMARY KEY (id_rol)
)
;
CREATE TABLE public.usuariosroles
(
    id_usuariorol serial,
    id_usuario integer,
    id_rol integer,
    id_usuarioauditoria integer,
    CONSTRAINT usuariosroles_id_usuariorol_pk PRIMARY KEY (id_usuariorol)
)
;
CREATE TABLE public.sistemas
(
    id_sistema serial,
    nombre_sistema character varying ,
    url_sistema character varying,
    id_usuarioauditoria integer,
    CONSTRAINT sistemas_id_sistema_pk PRIMARY KEY (id_sistema)
)
;
CREATE TABLE public.submenus
(
    id_submenu serial,
    nombre_submenu character varying,
    url_submenu character varying,
    id_usuarioauditoria integer,
    CONSTRAINT submenus_id_submenu_pk PRIMARY KEY (id_submenu)
)
;
CREATE TABLE public.formularios
(
    id_formulario serial,
    nombre_formulario character varying,
    url_formulario character varying,
    id_sistema integer,
    id_submenu integer,
    id_usuarioauditoria integer,
    CONSTRAINT formularios_id_formulario_pk PRIMARY KEY (id_formulario)
)
;
CREATE TABLE public.permisos
(
    id_permiso serial,
    id_rol integer,
    id_formulario integer,
    agregar_permiso boolean,
    modificar_permiso boolean,
    eliminar_permiso boolean,
    consultar_permiso boolean,
    listar_permiso boolean,
    informes_permiso boolean,
    exportar_permiso boolean,
    id_usuarioauditoria integer,
    CONSTRAINT permisos_id_permiso_pk PRIMARY KEY (id_permiso)
)