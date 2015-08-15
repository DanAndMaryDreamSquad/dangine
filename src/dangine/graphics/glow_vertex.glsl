#version 150 core

uniform mat4 transformMatrix;

in vec4 in_Position;
in vec4 in_Color;

out vec4 pass_Position;
out vec4 pass_Color;

void main(void) {
	gl_Position = in_Position;
	gl_Position.x = in_Position.x * 1.65;
	gl_Position.y = in_Position.y * 1.65;
	// Override gl_Position with our new calculated position
	gl_Position = transformMatrix * gl_Position;
	
	pass_Position = in_Position;
	pass_Color = in_Color;
}