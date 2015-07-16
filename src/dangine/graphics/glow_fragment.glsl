#version 150 core

uniform sampler2D texture_diffuse;

in vec4 pass_Color;
in vec4 pass_Position;

out vec4 out_Color;

void main(void) {
    vec3 borderColor = vec3(2.0, 2.0, 2.0) * pass_Color.xyz;
    float alpha = 0.0;
    float max = (0.5 * 0.5) + (0.5 * 0.5);
    if (
      (pass_Position.x < -0.25 && pass_Position.y < -0.25) ||
      (pass_Position.x > 0.25 && pass_Position.y > 0.25) ||
      (pass_Position.x < -0.25 && pass_Position.y > 0.25) ||
      (pass_Position.x > 0.25 && pass_Position.y < -0.25)    
    ) { // Corner
    
        float alpha_x = (0.5 - abs(pass_Position.x)) * 4.0;
        float alpha_y = (0.5 - abs(pass_Position.y)) * 4.0;
        alpha = alpha_x * alpha_y * 0.5;
    
        out_Color = vec4(borderColor.xyz, alpha);    
    } else if (pass_Position.x < -0.25 || pass_Position.x > 0.25 ) { // Left / Right border	
	    alpha = (0.5 - abs(pass_Position.x)) * 4.0 * 0.5;
	    out_Color = vec4(borderColor.xyz, alpha);	
	} else if (pass_Position.y < -0.25 || pass_Position.y > 0.25) { // Top / Bottom border
	    alpha = (0.5 - abs(pass_Position.y)) * 4.0 * 0.5;
	    out_Color = vec4(borderColor.xyz, alpha);    	
    } else {
	    out_Color = pass_Color;  
    }
}