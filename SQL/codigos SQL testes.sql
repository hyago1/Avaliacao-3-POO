SELECT * FROM public.dias_horarios
ORDER BY id ASC ;

select * from dias_semana;
select * from horarios;
select * from usuarios;

CREATE TABLE dias_horarios (
    id SERIAL PRIMARY KEY, -- Chave primária
    id_dia INT NOT NULL,               -- Chave estrangeira para `dias_semana`
    id_horario INT NOT NULL,
	vago boolean not null,
	agendado_por int not null,
    FOREIGN KEY (id_dia) REFERENCES dias_semana(id_dia) ON DELETE CASCADE,
    FOREIGN KEY (id_horario) REFERENCES horarios(id) ON DELETE CASCADE,
    FOREIGN KEY (agendado_por) REFERENCES usuarios(id) ON DELETE CASCADE
);

INSERT INTO dias_horarios (id_horario, id_dia, vago, agendado_por)
SELECT horarios.id, dias_semana.id_dia, true, 1
FROM dias_semana
CROSS JOIN horarios;

select id_horario, hora, usuarios.nome, nome_dia from  dias_horarios
join usuarios on usuarios.id = dias_horarios.agendado_por
join horarios on horarios.id = id_horario 
join dias_semana on dias_semana.id_dia = id_horario 
where dias_horarios.id_dia = 1;

-- Atualiza registro quando for agendar TELA AGENDAR HORARIO 
update dias_horarios set agendado_por = (select id from usuarios where nome = 'ze') , vago = false 
where id_dia = (select id_dia from dias_semana where nome_dia = 'Seg') and id_horario = 9; 

update dias_horarios set agendado_por = 1, vago = false 
where id_dia = (select id_dia from dias_semana where nome_dia = 'Seg') and id_horario = 5; 

-- ver horarios agendados pelo usuario
select hora, nome_dia from dias_horarios 
join horarios on horarios.id = id_horario 
join dias_semana on dias_semana.id_dia = dias_horarios.id_dia
where agendado_por = 4; 

-- ver horarios com base no dia TELA AGENDAR HORARIO
select id_horario,hora from dias_horarios
join horarios on id_horario = horarios.id
where id_dia = (select id_dia from dias_semana where nome_dia = 'Seg') and vago = true;/