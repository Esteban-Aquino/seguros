select id_permiso,
       r.id_rol,
       nombre_rol,
       p.id_formulario,
       nombre_formulario,
       url_formulario,
       s.id_sistema,
       nombre_sistema,
       url_sistema,
       f.id_submenu,
       nombre_submenu,
       url_submenu,
       p.id_usuarioauditoria,
       nombre_usuario,
       usuario_usuario,
       clave_usuario,
       r.id_rol,
       agregar_permiso,
       modificar_permiso,
       eliminar_permiso,
       consultar_permiso,
       listar_permiso,
       informes_permiso,
       exportar_permiso
from permisos p 
left join roles r on p.id_rol = r.id_rol
left join formularios f on p.id_formulario = f.id_formulario
left join sistemas s on f.id_sistema = s.id_sistema
left join submenus sm on f.id_submenu = sm.id_submenu
left join usuarios u on p.id_usuarioauditoria = u.id_usuario
where p.id_rol = 1
order by f.id_sistema,f.id_submenu,f.id_formulario