INSERT INTO categorias (name, description, ascii_icon)
VALUES
    ('General', 'Conversaciones generales sobre retro gaming', '[ ]'),
    ('Hardware', 'Consolas, cartuchos y periféricos retro', '[H]'),
    ('Reviews', 'Análisis y opiniones de juegos clásicos', '[R]'),
    ('Ayuda', 'Preguntas y soporte técnico', '[?]'),
    ('Off-Topic', 'Todo lo demás', '[*]')
ON CONFLICT DO NOTHING;